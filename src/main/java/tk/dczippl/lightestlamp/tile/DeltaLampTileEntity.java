package tk.dczippl.lightestlamp.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

public class DeltaLampTileEntity extends BlockEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    public DeltaLampTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public DeltaLampTileEntity()
    {
        super(ModTileEntities.DELTA_TE);
    }

    @Override
    public void tick()
    {
        //TODO: Change this to DELTA
        if (world.isRemote) return;

        cooldown++;

        if (cooldown == 5)
        {
            if (world.getRedstonePowerFromNeighbors(pos) > 0)
            {
                if (!getBlockState().get(POWERED))
                {
                    world.setBlockState(pos, getBlockState().with(POWERED, true));

                    BlockPos.getAllInBox(pos.offset(Direction.UP, 4).offset(Direction.NORTH, 4).offset(Direction.WEST, 4), pos.offset(Direction.DOWN, 4).offset(Direction.SOUTH, 4).offset(Direction.EAST, 4)).forEach((pos1) ->
                    {
                        if (world.getBlockState(pos1).getBlock() == ModBlocks.LIGHT_AIR.get())
                        {
                            world.setBlockState(pos1, Blocks.AIR.getDefaultState());
                        }
                    });
                }
            }
            else
            {
                if (getBlockState().get(POWERED))
                    world.setBlockState(pos, getBlockState().with(POWERED,false));
            }

            if (!getBlockState().get(POWERED))
            {
                BlockPos.getAllInBox(pos.offset(Direction.UP, 4).offset(Direction.NORTH, 4).offset(Direction.WEST, 4),
                        pos.offset(Direction.DOWN, 4).offset(Direction.SOUTH, 4).offset(Direction.EAST, 4)).forEach((pos2) ->
                {
                    if (isAir(pos2))
                    {
                        world.setBlockState(pos2, ModBlocks.LIGHT_AIR.get().getDefaultState());
                    }
                });
            }

            cooldown = 0;
        }
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR;
    }
}