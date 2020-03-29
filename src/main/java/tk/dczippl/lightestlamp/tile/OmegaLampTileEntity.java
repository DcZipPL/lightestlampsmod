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

        if (cooldown == 20)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15).offset(Direction.NORTH, 15).offset(Direction.WEST, 15),
                    pos.offset(Direction.UP, 1).offset(Direction.NORTH, 0).offset(Direction.WEST, 1)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                }
            });
        }
        if (cooldown == 30)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15).offset(Direction.SOUTH, 15).offset(Direction.WEST, 15),
                    pos.offset(Direction.UP, 1).offset(Direction.SOUTH, 1).offset(Direction.WEST, 0)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                }
            });
        }
        if (cooldown == 40)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15).offset(Direction.NORTH, 15).offset(Direction.EAST, 15),
                    pos.offset(Direction.UP, 1).offset(Direction.NORTH, 1).offset(Direction.EAST, 0)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                }
            });
        }
        if (cooldown == 50)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15).offset(Direction.SOUTH, 15).offset(Direction.EAST, 15),
                    pos.offset(Direction.UP, 1).offset(Direction.SOUTH, 0).offset(Direction.EAST, 1)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                }
            });
        }

        //---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//

        if (cooldown == 60)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15).offset(Direction.NORTH, 15).offset(Direction.WEST, 15),
                    pos.offset(Direction.NORTH, 1).offset(Direction.WEST, 0)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                }
            });
        }
        if (cooldown == 70)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15).offset(Direction.SOUTH, 15).offset(Direction.WEST, 15),
                    pos.offset(Direction.SOUTH, 0).offset(Direction.WEST, 1)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                }
            });
        }
        if (cooldown == 80)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15).offset(Direction.NORTH, 15).offset(Direction.EAST, 15),
                    pos.offset(Direction.NORTH, 0).offset(Direction.EAST, 1)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                }
            });
        }
        if (cooldown == 90)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15).offset(Direction.SOUTH, 15).offset(Direction.EAST, 15),
                    pos.offset(Direction.SOUTH, 1).offset(Direction.EAST, 0)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                }
            });
        }
        if (cooldown == 100)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15),
                    pos.offset(Direction.DOWN, 1)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                }
            });
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15),
                    pos.offset(Direction.UP, 1)).forEach((pos2) ->
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
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR;
    }
}