package tk.dczippl.lightestlamp.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

public class GammaLampTileEntity extends BlockEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    public GammaLampTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public GammaLampTileEntity()
    {
        super(ModTileEntities.GAMMA_TE);
    }

    @Override
    public void tick()
    {
        if (world.isRemote) return;

        cooldown++;

        if (cooldown == 5)
        {
            if (world.getRedstonePowerFromNeighbors(pos) > 0)
            {
                if (!getBlockState().get(POWERED))
                {
                    world.setBlockState(pos, getBlockState().with(POWERED, true));

                    BlockPos.getAllInBox(pos.offset(Direction.UP, 2).offset(Direction.NORTH, 2).offset(Direction.WEST, 2), pos.offset(Direction.DOWN, 2).offset(Direction.SOUTH, 2).offset(Direction.EAST, 2)).forEach((pos1) ->
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
                BlockPos.getAllInBox(pos.offset(Direction.UP, 2).offset(Direction.NORTH, 2).offset(Direction.WEST, 2),
                        pos.offset(Direction.DOWN, 2).offset(Direction.SOUTH, 2).offset(Direction.EAST, 2)).forEach((pos2) ->
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