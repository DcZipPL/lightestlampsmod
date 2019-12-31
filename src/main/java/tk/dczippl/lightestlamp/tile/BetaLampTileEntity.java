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

import java.util.Random;

public class BetaLampTileEntity extends TileEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    public BetaLampTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public BetaLampTileEntity()
    {
        super(ModTileEntities.BETA_TE);
    }

    @Override
    public void tick()
    {
        if (world.isRemote) return;

        cooldown++;

        if (cooldown == 5)
        {
            BlockPos pos1 = pos.offset(Direction.UP);
            if (world.getBlockState(pos1).getBlock() == Blocks.AIR || world.getBlockState(pos1).getBlock() == Blocks.CAVE_AIR)
            {
                world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
            }

            pos1 = pos.offset(Direction.DOWN);
            if (world.getBlockState(pos1).getBlock() == Blocks.AIR || world.getBlockState(pos1).getBlock() == Blocks.CAVE_AIR)
            {
                world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
            }

            pos1 = pos.offset(Direction.NORTH);
            if (world.getBlockState(pos1).getBlock() == Blocks.AIR || world.getBlockState(pos1).getBlock() == Blocks.CAVE_AIR)
            {
                world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
            }

            pos1 = pos.offset(Direction.SOUTH);
            if (world.getBlockState(pos1).getBlock() == Blocks.AIR || world.getBlockState(pos1).getBlock() == Blocks.CAVE_AIR)
            {
                world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
            }

            pos1 = pos.offset(Direction.WEST);
            if (world.getBlockState(pos1).getBlock() == Blocks.AIR || world.getBlockState(pos1).getBlock() == Blocks.CAVE_AIR)
            {
                world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
            }

            pos1 = pos.offset(Direction.EAST);
            if (world.getBlockState(pos1).getBlock() == Blocks.AIR || world.getBlockState(pos1).getBlock() == Blocks.CAVE_AIR)
            {
                world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
            }

            /*BlockPos.getAllInBox(pos.offset(Direction.UP, 1).offset(Direction.NORTH,1).offset(Direction.WEST,1), pos.offset(Direction.DOWN, 1).offset(Direction.SOUTH,1).offset(Direction.EAST,1)).forEach((pos2) -> {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                }
            });*/

            pos1 = pos.offset(Direction.UP,2);

            if (isAir(pos.offset(Direction.UP,1)))
                if (isAir(pos1))
                {
                    world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
                }

            pos1 = pos.offset(Direction.DOWN,2);

            if (isAir(pos.offset(Direction.DOWN,1)))
                if (isAir(pos1))
                {
                    world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
                }

            pos1 = pos.offset(Direction.NORTH,2);

            if (isAir(pos.offset(Direction.NORTH,1)))
                if (isAir(pos1))
                {
                    world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
                }

            pos1 = pos.offset(Direction.SOUTH,2);

            if (isAir(pos.offset(Direction.SOUTH,1)))
                if (isAir(pos1))
                {
                    world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
                }

            pos1 = pos.offset(Direction.WEST,2);

            if (isAir(pos.offset(Direction.WEST,1)))
                if (isAir(pos1))
                {
                    world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
                }

            pos1 = pos.offset(Direction.EAST,2);

            if (isAir(pos.offset(Direction.EAST,1)))
                if (isAir(pos1))
                {
                    world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
                }

            //SIDES

            pos1 = pos.offset(Direction.EAST,1).offset(Direction.UP,1).offset(Direction.NORTH,1);
            if (isAir(pos1))
            {
                if (isAir(pos.offset(Direction.EAST,1))||isAir(pos.offset(Direction.UP,1))||isAir(pos.offset(Direction.NORTH,1)))
                    world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
                else
                    world.setBlockState(pos1, Blocks.AIR.getDefaultState());
            }

            pos1 = pos.offset(Direction.WEST,1).offset(Direction.UP,1).offset(Direction.NORTH);
            if (isAir(pos1))
            {
                if (isAir(pos.offset(Direction.WEST,1))||isAir(pos.offset(Direction.UP,1))||isAir(pos.offset(Direction.NORTH,1)))
                    world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
                else
                    world.setBlockState(pos1, Blocks.AIR.getDefaultState());
            }

            pos1 = pos.offset(Direction.EAST,1).offset(Direction.UP,1).offset(Direction.SOUTH);
            if (isAir(pos1))
            {
                if (isAir(pos.offset(Direction.EAST,1))||isAir(pos.offset(Direction.UP,1))||isAir(pos.offset(Direction.SOUTH,1)))
                    world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
                else
                    world.setBlockState(pos1, Blocks.AIR.getDefaultState());
            }

            pos1 = pos.offset(Direction.WEST,1).offset(Direction.UP,1).offset(Direction.SOUTH);
            if (isAir(pos1))
            {
                if (isAir(pos.offset(Direction.WEST,1))||isAir(pos.offset(Direction.UP,1))||isAir(pos.offset(Direction.SOUTH,1)))
                    world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
                else
                    world.setBlockState(pos1, Blocks.AIR.getDefaultState());
            }

            //SIDES NEXT

            pos1 = pos.offset(Direction.EAST,1).offset(Direction.DOWN,1).offset(Direction.NORTH,1);
            if (isAir(pos1))
            {
                if (isAir(pos.offset(Direction.EAST,1))||isAir(pos.offset(Direction.DOWN,1))||isAir(pos.offset(Direction.NORTH,1)))
                    world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
                else
                    world.setBlockState(pos1, Blocks.AIR.getDefaultState());
            }

            pos1 = pos.offset(Direction.WEST,1).offset(Direction.DOWN,1).offset(Direction.NORTH);
            if (isAir(pos1))
            {
                if (isAir(pos.offset(Direction.WEST,1))||isAir(pos.offset(Direction.DOWN,1))||isAir(pos.offset(Direction.NORTH,1)))
                    world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
                else
                    world.setBlockState(pos1, Blocks.AIR.getDefaultState());
            }

            pos1 = pos.offset(Direction.EAST,1).offset(Direction.DOWN,1).offset(Direction.SOUTH);
            if (isAir(pos1))
            {
                if (isAir(pos.offset(Direction.EAST,1))||isAir(pos.offset(Direction.DOWN,1))||isAir(pos.offset(Direction.SOUTH,1)))
                    world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
                else
                    world.setBlockState(pos1, Blocks.AIR.getDefaultState());
            }

            pos1 = pos.offset(Direction.WEST,1).offset(Direction.DOWN,1).offset(Direction.SOUTH);
            if (isAir(pos1))
            {
                if (isAir(pos.offset(Direction.WEST,1))||isAir(pos.offset(Direction.DOWN,1))||isAir(pos.offset(Direction.SOUTH,1)))
                    world.setBlockState(pos1, ModBlocks.LIGHT_AIR.getDefaultState());
                else
                    world.setBlockState(pos1, Blocks.AIR.getDefaultState());
            }

            //END OF SIDES

            /*Random r = new Random();
            if (r.nextInt(80) == 2)
            {
                BlockPos.getAllInBox(pos.offset(Direction.UP, 2).offset(Direction.NORTH,2).offset(Direction.WEST,2), pos.offset(Direction.DOWN, 2).offset(Direction.SOUTH,2).offset(Direction.EAST,2)).forEach((pos2) -> {
                    if (world.getBlockState(pos2).getBlock() == ModBlocks.LIGHT_AIR)
                    {
                        world.setBlockState(pos2, Blocks.AIR.getDefaultState());
                    }
                });
            }*/
            cooldown = 0;
        }
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || world.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR;
    }
}