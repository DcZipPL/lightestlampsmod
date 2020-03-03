package tk.dczippl.lightestlamp.machine.lumination_core;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import tk.dczippl.lightestlamp.util.CustomEnergyStorage;

public class LuminationCoreTileEntity extends TileEntity implements ITickableTileEntity
{
    private static final int ENERGY_COST = 600;
    static final int MAX = 640000;
    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(this::createEnergy);

    public LuminationCoreTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    private IEnergyStorage createEnergy() {
        return new CustomEnergyStorage(MAX, MAX / 2);
    }

    @Override
    public void tick()
    {
        energy.orElse(null).extractEnergy(ENERGY_COST,false);
    }
}
