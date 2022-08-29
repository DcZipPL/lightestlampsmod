package tk.dczippl.lightestlamp.tile.cleaners;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModBlockEntities;

public class OmegaChunkCleanerTileEntity extends BlockEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    public OmegaChunkCleanerTileEntity(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }

    public OmegaChunkCleanerTileEntity()
    {
        super(ModBlockEntities.OCC_TE);
    }

    @Override
    public void tick()
    {
        if (world.isRemote) return;

        if (cooldown == 1)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15).offset(Direction.NORTH, 15).offset(Direction.WEST, 15),
                    pos.offset(Direction.UP, 1).offset(Direction.NORTH, 0).offset(Direction.WEST, 1)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, Blocks.AIR.getDefaultState());
                }
            });
        }
        if (cooldown == 2)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15).offset(Direction.SOUTH, 15).offset(Direction.WEST, 15),
                    pos.offset(Direction.UP, 1).offset(Direction.SOUTH, 1).offset(Direction.WEST, 0)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, Blocks.AIR.getDefaultState());
                }
            });
        }
        if (cooldown == 3)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15).offset(Direction.NORTH, 15).offset(Direction.EAST, 15),
                    pos.offset(Direction.UP, 1).offset(Direction.NORTH, 1).offset(Direction.EAST, 0)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, Blocks.AIR.getDefaultState());
                }
            });
        }
        if (cooldown == 4)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15).offset(Direction.SOUTH, 15).offset(Direction.EAST, 15),
                    pos.offset(Direction.UP, 1).offset(Direction.SOUTH, 0).offset(Direction.EAST, 1)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, Blocks.AIR.getDefaultState());
                }
            });
        }

        //---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//

        if (cooldown == 5)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15).offset(Direction.NORTH, 15).offset(Direction.WEST, 15),
                    pos.offset(Direction.NORTH, 1).offset(Direction.WEST, 0)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, Blocks.AIR.getDefaultState());
                }
            });
        }
        if (cooldown == 6)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15).offset(Direction.SOUTH, 15).offset(Direction.WEST, 15),
                    pos.offset(Direction.SOUTH, 0).offset(Direction.WEST, 1)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, Blocks.AIR.getDefaultState());
                }
            });
        }
        if (cooldown == 7)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15).offset(Direction.NORTH, 15).offset(Direction.EAST, 15),
                    pos.offset(Direction.NORTH, 0).offset(Direction.EAST, 1)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, Blocks.AIR.getDefaultState());
                }
            });
        }
        if (cooldown == 8)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15).offset(Direction.SOUTH, 15).offset(Direction.EAST, 15),
                    pos.offset(Direction.SOUTH, 1).offset(Direction.EAST, 0)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, Blocks.AIR.getDefaultState());
                }
            });
        }
        if (cooldown == 9)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15),
                    pos.offset(Direction.DOWN, 1)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, Blocks.AIR.getDefaultState());
                }
            });
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15),
                    pos.offset(Direction.UP, 1)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, Blocks.AIR.getDefaultState());
                }
            });
        }
        if (cooldown >= 20)
        {
            /*BlockPos.getAllInBox(pos.offset(Direction.UP, 19).offset(Direction.NORTH,19).offset(Direction.WEST,19), pos.offset(Direction.DOWN,19).offset(Direction.SOUTH,19).offset(Direction.EAST,19)).forEach((pos1) -> {
                if (isAir(pos1))
                    world.notifyBlockUpdate(pos1,world.getBlockState(pos1),world.getBlockState(pos1),3);
            });*/
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.setTileEntity(pos, null);
            //Recalc Light in nearby chunks

            cooldown = 0;
        }
        cooldown++;
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || world.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR.get();
    }
}