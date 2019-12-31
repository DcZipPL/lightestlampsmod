package tk.dczippl.lightestlamp.util.capability;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class ArgonStorage implements Capability.IStorage<Argon>
{
    @Nullable
    @Override
    public INBTBase writeNBT(Capability<Argon> capability, Argon instance, EnumFacing side)
    {
        return new NBTTagFloat(instance.getArgon());
    }

    @Override
    public void readNBT(Capability<Argon> capability, Argon instance, EnumFacing side, INBTBase nbt)
    {
        instance.set(((NBTPrimitive) nbt).getFloat());
    }
}