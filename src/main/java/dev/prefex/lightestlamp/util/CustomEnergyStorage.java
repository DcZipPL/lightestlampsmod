package dev.prefex.lightestlamp.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage implements INBTSerializable<Tag> {

    public CustomEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public void changeTransferRate(int tr){
        maxReceive = tr;
    }

    public void setEnergy(int energy) {
        this.energy = Math.clamp(energy, 0, capacity);
    }

    public void addEnergy(int energy) {
        setEnergy(this.energy + energy);
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("energy", getEnergyStored());
        return tag;
    }

    @Override // TODO: It works?
    public void deserializeNBT(HolderLookup.Provider provider, Tag nbt) {
        setEnergy(((CompoundTag)nbt).getInt("energy"));
    }
}
