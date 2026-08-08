package dev.prefex.lightestlamp.lighting;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final class LampLightSavedData extends SavedData {
    private static final String OWNERS = "Owners";
    private static final String OWNER = "Owner";
    private static final String SOURCES = "Sources";

    static final Factory<LampLightSavedData> FACTORY = new Factory<>(
            LampLightSavedData::new,
            LampLightSavedData::load,
            DataFixTypes.SAVED_DATA_MAP_DATA
    );

    private final Map<Long, Set<Long>> sourcesByOwner = new HashMap<>();

    private static LampLightSavedData load(CompoundTag tag, HolderLookup.Provider registries) {
        LampLightSavedData data = new LampLightSavedData();
        ListTag owners = tag.getList(OWNERS, Tag.TAG_COMPOUND);
        for (int i = 0; i < owners.size(); i++) {
            CompoundTag ownerTag = owners.getCompound(i);
            Set<Long> sources = new HashSet<>();
            for (long source : ownerTag.getLongArray(SOURCES)) {
                sources.add(source);
            }
            data.sourcesByOwner.put(ownerTag.getLong(OWNER), sources);
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag owners = new ListTag();
        sourcesByOwner.forEach((owner, sources) -> {
            CompoundTag ownerTag = new CompoundTag();
            ownerTag.putLong(OWNER, owner);
            ownerTag.putLongArray(SOURCES, sources.stream().mapToLong(Long::longValue).toArray());
            owners.add(ownerTag);
        });
        tag.put(OWNERS, owners);
        return tag;
    }

    Map<Long, Set<Long>> owners() {
        return Collections.unmodifiableMap(sourcesByOwner);
    }

    Set<Long> sources(long owner) {
        return sourcesByOwner.getOrDefault(owner, Collections.emptySet());
    }

    void setSources(long owner, Set<Long> sources) {
        if (sources.isEmpty()) {
            sourcesByOwner.remove(owner);
        } else {
            sourcesByOwner.put(owner, new HashSet<>(sources));
        }
        setDirty();
    }
}
