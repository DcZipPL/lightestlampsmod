package dev.prefex.lightestlamp.lighting;

import dev.prefex.lightestlamp.Config;
import dev.prefex.lightestlamp.blocks.LightestLampBlock;
import dev.prefex.lightestlamp.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

/**
 * Server-authoritative owner/ref-count manager for the air light sources.
 * The manager is intentionally idle when there are no dirty lamps or pending mutations.
 */
public final class LampLightManager {
    private static final String DATA_NAME = "lightestlamp_light_sources";
    private static final Map<ServerLevel, LevelState> LEVELS = Collections.synchronizedMap(new WeakHashMap<>());

    private LampLightManager() {}

    public static void register(ServerLevel level, BlockPos pos, BlockState state) {
        if (!(state.getBlock() instanceof LightestLampBlock lamp)) {
            return;
        }
        levelState(level).register(pos, lamp.profile());
    }

    public static void unregister(ServerLevel level, BlockPos pos) {
        levelState(level).unregister(pos);
    }

    public static void schedule(ServerLevel level, BlockPos pos, int delay) {
        levelState(level).schedule(pos.asLong(), Math.max(0, delay));
    }

    public static void topologyChanged(ServerLevel level, BlockPos pos) {
        levelState(level).topologyChanged(pos);
    }

    public static void reconcileChunk(ServerLevel level, ChunkPos chunkPos) {
        levelState(level).chunksToReconcile.add(chunkPos.toLong());
    }

    public static void unloadChunk(ServerLevel level, ChunkPos chunkPos) {
        levelState(level).unloadChunk(chunkPos);
    }

    public static Budget tick(ServerLevel level, Budget budget) {
        return levelState(level).tick(budget);
    }

    public static void forget(ServerLevel level) {
        LEVELS.remove(level);
    }

    private static LevelState levelState(ServerLevel level) {
        return LEVELS.computeIfAbsent(level, LevelState::new);
    }

    public record Budget(int traversals, int mutations) {
        public static Budget configured() {
            return new Budget(Config.LIGHT_TRAVERSAL_BUDGET.get(), Config.LIGHT_MUTATION_BUDGET.get());
        }
    }

    private static final class LevelState {
        private final ServerLevel level;
        private final LampLightSavedData data;
        private final Map<Long, Integer> sourceReferences = new HashMap<>();
        private final Map<Long, Set<Long>> sourcesByChunk = new HashMap<>();
        private final Map<Long, LampProfile> activeProfiles = new HashMap<>();
        private final Map<Long, Set<Long>> ownersByChunk = new HashMap<>();
        private final Map<Long, Set<Long>> persistedOwnersByChunk = new HashMap<>();
        private final Map<Long, Long> dueOwners = new HashMap<>();
        private final ArrayDeque<SourceMutation> removals = new ArrayDeque<>();
        private final ArrayDeque<SourceMutation> additions = new ArrayDeque<>();
        private final ArrayDeque<Long> chunksToReconcile = new ArrayDeque<>();
        private PlannerJob currentJob;

        private LevelState(ServerLevel level) {
            this.level = level;
            this.data = level.getDataStorage().computeIfAbsent(LampLightSavedData.FACTORY, DATA_NAME);
            data.owners().forEach((owner, sources) -> {
                indexPersistedOwner(owner);
                sources.forEach(source -> {
                    if (sourceReferences.merge(source, 1, Integer::sum) == 1) {
                        indexSource(source);
                    }
                });
            });
        }

        private void register(BlockPos pos, LampProfile profile) {
            long owner = pos.asLong();
            LampProfile previous = activeProfiles.put(owner, profile);
            if (previous == null) {
                ownersByChunk.computeIfAbsent(ChunkPos.asLong(pos), ignored -> new HashSet<>()).add(owner);
            }
            schedule(owner, 0);
        }

        private void unregister(BlockPos pos) {
            long owner = pos.asLong();
            activeProfiles.remove(owner);
            dueOwners.remove(owner);
            Set<Long> chunkOwners = ownersByChunk.get(ChunkPos.asLong(pos));
            if (chunkOwners != null) {
                chunkOwners.remove(owner);
                if (chunkOwners.isEmpty()) {
                    ownersByChunk.remove(ChunkPos.asLong(pos));
                }
            }
            replaceSources(owner, Collections.emptySet());
            if (currentJob != null && currentJob.owner == owner) {
                currentJob = null;
            }
        }

        private void schedule(long owner, int delay) {
            if (!activeProfiles.containsKey(owner) && !data.owners().containsKey(owner)) {
                return;
            }
            long due = level.getGameTime() + delay;
            dueOwners.merge(owner, due, Math::min);
        }

        private void topologyChanged(BlockPos changed) {
            int chunkX = SectionMath.blockToChunk(changed.getX());
            int chunkZ = SectionMath.blockToChunk(changed.getZ());
            int debounce = Config.LIGHT_TOPOLOGY_DEBOUNCE.get();
            for (int x = chunkX - 1; x <= chunkX + 1; x++) {
                for (int z = chunkZ - 1; z <= chunkZ + 1; z++) {
                    Set<Long> owners = ownersByChunk.get(ChunkPos.asLong(x, z));
                    if (owners == null) {
                        continue;
                    }
                    for (long owner : owners) {
                        LampProfile profile = activeProfiles.get(owner);
                        BlockPos ownerPos = BlockPos.of(owner);
                        if (profile != null && ownerPos.distManhattan(changed) <= profile.extension()) {
                            schedule(owner, debounce);
                        }
                    }
                }
            }
        }

        private void unloadChunk(ChunkPos chunkPos) {
            Set<Long> owners = ownersByChunk.remove(chunkPos.toLong());
            if (owners == null) {
                return;
            }
            owners.forEach(owner -> {
                activeProfiles.remove(owner);
                dueOwners.remove(owner);
                if (currentJob != null && currentJob.owner == owner) {
                    currentJob = null;
                }
            });
            // Persisted ownership and source references deliberately remain. They keep already-saved
            // source blocks valid without forcing the lamp's chunk to stay loaded.
        }

        private Budget tick(Budget budget) {
            int mutations = budget.mutations();
            while (mutations > 0 && !removals.isEmpty()) {
                apply(removals.removeFirst());
                mutations--;
            }
            while (mutations > 0 && !additions.isEmpty()) {
                apply(additions.removeFirst());
                mutations--;
            }

            if (!chunksToReconcile.isEmpty() && budget.traversals() > 0) {
                reconcile(chunksToReconcile.removeFirst());
            }

            int traversals = budget.traversals();
            while (traversals > 0) {
                if (currentJob == null) {
                    currentJob = nextJob();
                    if (currentJob == null) {
                        break;
                    }
                }
                int used = currentJob.run(Math.min(traversals, 512));
                traversals -= used;
                if (currentJob.complete) {
                    replaceSources(currentJob.owner, currentJob.targets);
                    currentJob = null;
                }
                if (used == 0) {
                    break;
                }
            }
            return new Budget(traversals, mutations);
        }

        private PlannerJob nextJob() {
            Iterator<Map.Entry<Long, Long>> iterator = dueOwners.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Long, Long> entry = iterator.next();
                if (entry.getValue() > level.getGameTime()) {
                    continue;
                }
                long owner = entry.getKey();
                iterator.remove();
                BlockPos pos = BlockPos.of(owner);
                if (!level.hasChunkAt(pos)) {
                    continue;
                }
                BlockState state = level.getBlockState(pos);
                if (!(state.getBlock() instanceof LightestLampBlock lamp)) {
                    unregister(pos);
                    continue;
                }
                LampProfile profile = lamp.profile();
                registerProfileOnly(owner, profile);
                if (!lamp.isActive(state)) {
                    replaceSources(owner, Collections.emptySet());
                    continue;
                }
                return new PlannerJob(owner, pos, profile);
            }
            return null;
        }

        private void registerProfileOnly(long owner, LampProfile profile) {
            LampProfile previous = activeProfiles.put(owner, profile);
            if (previous == null) {
                BlockPos pos = BlockPos.of(owner);
                ownersByChunk.computeIfAbsent(ChunkPos.asLong(pos), ignored -> new HashSet<>()).add(owner);
            }
        }

        private void replaceSources(long owner, Set<Long> targets) {
            Set<Long> oldSources = new HashSet<>(data.sources(owner));
            if (oldSources.equals(targets)) {
                return;
            }
            for (long source : oldSources) {
                if (!targets.contains(source)) {
                    int remaining = sourceReferences.merge(source, -1, Integer::sum);
                    if (remaining <= 0) {
                        sourceReferences.remove(source);
                        unindexSource(source);
                        removals.addLast(new SourceMutation(source, false));
                    }
                }
            }
            for (long source : targets) {
                if (!oldSources.contains(source)) {
                    int previous = sourceReferences.getOrDefault(source, 0);
                    sourceReferences.put(source, previous + 1);
                    if (previous == 0) {
                        indexSource(source);
                        additions.addLast(new SourceMutation(source, true));
                    }
                }
            }
            data.setSources(owner, targets);
            if (targets.isEmpty()) {
                unindexPersistedOwner(owner);
            } else {
                indexPersistedOwner(owner);
            }
        }

        private void apply(SourceMutation mutation) {
            BlockPos pos = BlockPos.of(mutation.pos);
            if (!level.hasChunkAt(pos)) {
                return;
            }
            BlockState current = level.getBlockState(pos);
            if (mutation.add) {
                if (sourceReferences.getOrDefault(mutation.pos, 0) <= 0 || !isSourceMedium(current, true)) {
                    return;
                }
                boolean water = current.getFluidState().is(Fluids.WATER) && current.getFluidState().isSource();
                BlockState source = ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().defaultBlockState().setValue(WATERLOGGED, water);
                level.setBlock(pos, source, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
            } else {
                if (sourceReferences.containsKey(mutation.pos)
                        || (!isManagedSource(current) && !current.is(ModBlocks.OCC.get()))) {
                    return;
                }
                BlockState replacement = current.hasProperty(WATERLOGGED) && current.getValue(WATERLOGGED)
                        ? Blocks.WATER.defaultBlockState()
                        : Blocks.AIR.defaultBlockState();
                level.setBlock(pos, replacement, Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
            }
            level.getChunkSource().getLightEngine().checkBlock(pos);
        }

        private void reconcile(long packedChunk) {
            int chunkX = ChunkPos.getX(packedChunk);
            int chunkZ = ChunkPos.getZ(packedChunk);
            LevelChunk chunk = level.getChunkSource().getChunkNow(chunkX, chunkZ);
            if (chunk == null) {
                return;
            }

            List<BlockEntity> blockEntities = new ArrayList<>(chunk.getBlockEntities().values());
            for (BlockEntity blockEntity : blockEntities) {
                BlockState state = blockEntity.getBlockState();
                if (state.getBlock() instanceof LightestLampBlock lamp) {
                    register(blockEntity.getBlockPos(), lamp.profile());
                }
            }

            List<Long> storedOwners = new ArrayList<>(persistedOwnersByChunk.getOrDefault(packedChunk, Collections.emptySet()));
            for (long owner : storedOwners) {
                BlockPos ownerPos = BlockPos.of(owner);
                if (SectionMath.blockToChunk(ownerPos.getX()) == chunkX && SectionMath.blockToChunk(ownerPos.getZ()) == chunkZ
                        && !(level.getBlockState(ownerPos).getBlock() instanceof LightestLampBlock)) {
                    unregister(ownerPos);
                }
            }

            Set<Long> expectedSources = sourcesByChunk.getOrDefault(packedChunk, Collections.emptySet());
            expectedSources.forEach(source -> {
                BlockPos sourcePos = BlockPos.of(source);
                if (sourceReferences.getOrDefault(source, 0) > 0
                        && !isManagedSource(level.getBlockState(sourcePos))) {
                    additions.addLast(new SourceMutation(source, true));
                }
            });

            LevelChunkSection[] sections = chunk.getSections();
            for (int sectionIndex = 0; sectionIndex < sections.length; sectionIndex++) {
                LevelChunkSection section = sections[sectionIndex];
                if (!section.getStates().maybeHas(state -> isManagedSource(state) || state.is(ModBlocks.OCC.get()))) {
                    continue;
                }
                int minY = chunk.getSectionYFromSectionIndex(sectionIndex) << 4;
                for (int y = 0; y < 16; y++) {
                    for (int z = 0; z < 16; z++) {
                        for (int x = 0; x < 16; x++) {
                            BlockState state = section.getBlockState(x, y, z);
                            if (!isManagedSource(state) && !state.is(ModBlocks.OCC.get())) {
                                continue;
                            }
                            BlockPos pos = new BlockPos((chunkX << 4) + x, minY + y, (chunkZ << 4) + z);
                            if (state.is(ModBlocks.OCC.get()) || sourceReferences.getOrDefault(pos.asLong(), 0) <= 0) {
                                removals.addLast(new SourceMutation(pos.asLong(), false));
                            }
                        }
                    }
                }
            }
        }

        private void indexSource(long source) {
            BlockPos pos = BlockPos.of(source);
            sourcesByChunk.computeIfAbsent(ChunkPos.asLong(pos), ignored -> new HashSet<>()).add(source);
        }

        private void indexPersistedOwner(long owner) {
            BlockPos pos = BlockPos.of(owner);
            persistedOwnersByChunk.computeIfAbsent(ChunkPos.asLong(pos), ignored -> new HashSet<>()).add(owner);
        }

        private void unindexPersistedOwner(long owner) {
            BlockPos pos = BlockPos.of(owner);
            long chunk = ChunkPos.asLong(pos);
            Set<Long> owners = persistedOwnersByChunk.get(chunk);
            if (owners != null) {
                owners.remove(owner);
                if (owners.isEmpty()) {
                    persistedOwnersByChunk.remove(chunk);
                }
            }
        }

        private void unindexSource(long source) {
            BlockPos pos = BlockPos.of(source);
            long chunk = ChunkPos.asLong(pos);
            Set<Long> sources = sourcesByChunk.get(chunk);
            if (sources != null) {
                sources.remove(source);
                if (sources.isEmpty()) {
                    sourcesByChunk.remove(chunk);
                }
            }
        }

        private final class PlannerJob {
            private final long owner;
            private final LampProfile profile;
            private final ArrayDeque<Node> open = new ArrayDeque<>();
            private final Set<Long> visited = new HashSet<>();
            private final Set<Long> targets = new HashSet<>();
            private boolean complete;

            private PlannerJob(long owner, BlockPos pos, LampProfile profile) {
                this.owner = owner;
                this.profile = profile;
                open.addLast(new Node(pos.asLong(), 0));
                visited.add(pos.asLong());
            }

            private int run(int budget) {
                int used = 0;
                while (used < budget && !open.isEmpty()) {
                    Node node = open.removeFirst();
                    used++;
                    BlockPos pos = BlockPos.of(node.pos);
                    if (profile.shouldPlaceSourceAt(node.distance)
                            && isSourceMedium(level.getBlockState(pos), profile.medium() == LampProfile.Medium.UNDERWATER)) {
                        targets.add(node.pos);
                    }
                    if (node.distance >= profile.extension()) {
                        continue;
                    }
                    BlockState fromState = level.getBlockState(pos);
                    for (Direction direction : Direction.values()) {
                        BlockPos next = pos.relative(direction);
                        long packed = next.asLong();
                        if (visited.contains(packed) || !level.hasChunkAt(next)) {
                            continue;
                        }
                        BlockState nextState = level.getBlockState(next);
                        if (!profile.luminant()) {
                            int opacity = Math.max(1, nextState.getLightBlock(level, next));
                            int blocked = LightEngine.getLightBlockInto(level, fromState, pos, nextState, next, direction, opacity);
                            if (blocked >= 15) {
                                continue;
                            }
                        }
                        int distance = node.distance + 1;
                        if (distance > profile.extension()) {
                            continue;
                        }
                        visited.add(packed);
                        open.addLast(new Node(packed, distance));
                    }
                }
                complete = open.isEmpty();
                return used;
            }
        }
    }

    private static boolean isSourceMedium(BlockState state, boolean underwater) {
        if (isManagedSource(state) || state.isAir()) {
            return true;
        }
        return underwater && state.getFluidState().is(Fluids.WATER) && state.getFluidState().isSource();
    }

    private static boolean isManagedSource(BlockState state) {
        return state.is(ModBlocks.LIGHT_AIR.get()) || state.is(ModBlocks.WATERLOGGABLE_LIGHT_AIR.get());
    }

    private record Node(long pos, int distance) {}
    private record SourceMutation(long pos, boolean add) {}

    private static final class SectionMath {
        private static int blockToChunk(int coordinate) {
            return coordinate >> 4;
        }
    }
}
