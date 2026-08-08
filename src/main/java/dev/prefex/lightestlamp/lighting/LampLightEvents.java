package dev.prefex.lightestlamp.lighting;

import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.level.PistonEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

public final class LampLightEvents {
    private LampLightEvents() {}

    public static void init() {
        NeoForge.EVENT_BUS.addListener(LampLightEvents::serverTick);
        NeoForge.EVENT_BUS.addListener(LampLightEvents::chunkLoad);
        NeoForge.EVENT_BUS.addListener(LampLightEvents::chunkUnload);
        NeoForge.EVENT_BUS.addListener(LampLightEvents::levelUnload);
        NeoForge.EVENT_BUS.addListener(LampLightEvents::blockPlaced);
        NeoForge.EVENT_BUS.addListener(LampLightEvents::blockBroken);
        NeoForge.EVENT_BUS.addListener(LampLightEvents::neighborChanged);
        NeoForge.EVENT_BUS.addListener(LampLightEvents::fluidChanged);
        NeoForge.EVENT_BUS.addListener(LampLightEvents::pistonMoved);
        NeoForge.EVENT_BUS.addListener(LampLightEvents::exploded);
    }

    private static void serverTick(ServerTickEvent.Post event) {
        LampLightManager.Budget budget = LampLightManager.Budget.configured();
        for (ServerLevel level : event.getServer().getAllLevels()) {
            budget = LampLightManager.tick(level, budget);
            if (budget.traversals() <= 0 && budget.mutations() <= 0) {
                break;
            }
        }
    }

    private static void chunkLoad(ChunkEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel level) {
            LampLightManager.reconcileChunk(level, event.getChunk().getPos());
        }
    }

    private static void chunkUnload(ChunkEvent.Unload event) {
        if (event.getLevel() instanceof ServerLevel level) {
            LampLightManager.unloadChunk(level, event.getChunk().getPos());
        }
    }

    private static void levelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof ServerLevel level) {
            LampLightManager.forget(level);
        }
    }

    private static void blockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getLevel() instanceof ServerLevel level) {
            LampLightManager.topologyChanged(level, event.getPos());
        }
    }

    private static void blockBroken(BlockEvent.BreakEvent event) {
        if (!event.isCanceled() && event.getLevel() instanceof ServerLevel level) {
            LampLightManager.topologyChanged(level, event.getPos());
        }
    }

    private static void neighborChanged(BlockEvent.NeighborNotifyEvent event) {
        if (event.getLevel() instanceof ServerLevel level) {
            LampLightManager.topologyChanged(level, event.getPos());
        }
    }

    private static void fluidChanged(BlockEvent.FluidPlaceBlockEvent event) {
        if (!event.isCanceled() && event.getLevel() instanceof ServerLevel level) {
            LampLightManager.topologyChanged(level, event.getPos());
        }
    }

    private static void pistonMoved(PistonEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel level) {
            LampLightManager.topologyChanged(level, event.getPos());
            event.getStructureHelper().getToPush().forEach(pos -> LampLightManager.topologyChanged(level, pos));
            event.getStructureHelper().getToDestroy().forEach(pos -> LampLightManager.topologyChanged(level, pos));
        }
    }

    private static void exploded(ExplosionEvent.Detonate event) {
        if (event.getLevel() instanceof ServerLevel level) {
            event.getAffectedBlocks().forEach(pos -> LampLightManager.topologyChanged(level, pos));
        }
    }
}
