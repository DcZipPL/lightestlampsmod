package tk.dczippl.lightestlamp.tile;

import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

public class OmegaLampTileEntity extends TileEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    public OmegaLampTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public OmegaLampTileEntity()
    {
        super(ModTileEntities.OMEGA_TE);
    }

    @Override
    public void tick()
    {
        if (world.isRemote) return;

        cooldown++;

        if (cooldown == 5)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 10).offset(Direction.NORTH, 10).offset(Direction.WEST, 10),
                    pos.offset(Direction.DOWN, 10).offset(Direction.SOUTH, 10).offset(Direction.EAST, 10)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                }
            });

            cooldown = 0;
        }
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || world.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR;
    }
}