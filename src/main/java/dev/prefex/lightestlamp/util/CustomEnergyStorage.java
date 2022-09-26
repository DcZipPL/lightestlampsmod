package dev.prefex.lightestlamp.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage implements INBTSerializable<Tag> {

    public CustomEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public void changeTransferRate(int tr){
        maxReceive = tr;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
        if (this.energy > getMaxEnergyStored()) {
            this.energy = getEnergyStored();
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("energy", getEnergyStored());
        return tag;
    }

    @Override // TODO: It works?
    public void deserializeNBT(Tag nbt) {
        super.deserializeNBT(nbt);
        setEnergy(((CompoundTag)nbt).getInt("energy"));
    }
}