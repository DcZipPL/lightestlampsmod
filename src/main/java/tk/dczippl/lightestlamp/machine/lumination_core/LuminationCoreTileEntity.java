package tk.dczippl.lightestlamp.machine.lumination_core;

import mekanism.api.MekanismAPI;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;
import tk.dczippl.lightestlamp.util.CustomEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LuminationCoreTileEntity extends TileEntity implements ITickableTileEntity
{
    private int cooldown = 0;
    private static final int ENERGY_COST = 600;
    static final int MAX = 640000;
    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(this::createEnergy);

    public LuminationCoreTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }
    public LuminationCoreTileEntity() {
        super(ModTileEntities.LUMINATOR_TE);
    }

    private IEnergyStorage createEnergy() {
        return new CustomEnergyStorage(MAX, MAX / 2);
    }

    @Override
    public void tick()
    {
        if (cooldown == 20)
        {
            if (energy.orElse(null).getEnergyStored() > ENERGY_COST * 2)
            {
                energy.orElse(null).extractEnergy(ENERGY_COST, false);
                BlockPos.getAllInBox(pos.offset(Direction.UP, 24).offset(Direction.NORTH, 24).offset(Direction.WEST, 24),
                        pos.offset(Direction.DOWN, 24).offset(Direction.SOUTH, 24).offset(Direction.EAST, 24)).forEach((pos2) ->
                {
                    if (isAir(pos2))
                    {
                        world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                    }
                });
            } else
            {
                BlockPos.getAllInBox(pos.offset(Direction.UP, 25).offset(Direction.NORTH, 25).offset(Direction.WEST, 25), pos.offset(Direction.DOWN, 25).offset(Direction.SOUTH, 25).offset(Direction.EAST, 25)).forEach((pos1) ->
                {
                    if (world.getBlockState(pos1).getBlock() == ModBlocks.LIGHT_AIR)
                    {
                        world.setBlockState(pos1, Blocks.AIR.getDefaultState());
                    }
                });
                BlockPos.getAllInBox(pos.offset(Direction.UP, 25).offset(Direction.NORTH,25).offset(Direction.WEST,25), pos.offset(Direction.DOWN,25).offset(Direction.SOUTH,25).offset(Direction.EAST,25)).forEach((pos1) -> {
                    if (isAir(pos1))
                    {
                        world.notifyBlockUpdate(pos1, world.getBlockState(pos1), world.getBlockState(pos1), 3);
                    }
                });
            }
            cooldown = 0;
        }
        else
        cooldown++;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityEnergy.ENERGY)
            return energy.cast();
        else
            return super.getCapability(cap, side);
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR;
    }
}
