package tk.dczippl.lightestlamp.tile;

import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

import java.util.Random;

public class OmegaLampTileEntity extends TileEntity implements ITickableTileEntity
{
    private int cooldown = 0;
    private boolean updated = false;

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

        Random random = new Random();
        
        if (cooldown == 20)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15).offset(Direction.NORTH, 15).offset(Direction.WEST, 15),
                    pos.offset(Direction.UP, 1).offset(Direction.NORTH, 0).offset(Direction.WEST, 1)).forEach((pos2) ->
            {
                if (isAir(pos2)&&world.getBlockState(pos2.up()).getBlock() != Blocks.VINE)
                {
					if(random.nextInt(20)==5)
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.get().getDefaultState());
                }
            });
        }
        if (cooldown == 30)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15).offset(Direction.SOUTH, 15).offset(Direction.WEST, 15),
                    pos.offset(Direction.UP, 1).offset(Direction.SOUTH, 1).offset(Direction.WEST, 0)).forEach((pos2) ->
            {
                if (isAir(pos2)&&world.getBlockState(pos2.up()).getBlock() != Blocks.VINE)
                {
					if(random.nextInt(20)==5)
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.get().getDefaultState());
                }
            });
        }
        if (cooldown == 40)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15).offset(Direction.NORTH, 15).offset(Direction.EAST, 15),
                    pos.offset(Direction.UP, 1).offset(Direction.NORTH, 1).offset(Direction.EAST, 0)).forEach((pos2) ->
            {
                if (isAir(pos2)&&world.getBlockState(pos2.up()).getBlock() != Blocks.VINE)
                {
					if(random.nextInt(20)==5)
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.get().getDefaultState());
                }
            });
        }
        if (cooldown == 50)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15).offset(Direction.SOUTH, 15).offset(Direction.EAST, 15),
                    pos.offset(Direction.UP, 1).offset(Direction.SOUTH, 0).offset(Direction.EAST, 1)).forEach((pos2) ->
            {
                if (isAir(pos2)&&world.getBlockState(pos2.up()).getBlock() != Blocks.VINE)
                {
					if(random.nextInt(20)==5)
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.get().getDefaultState());
                }
            });
        }

        //---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//---//

        if (cooldown == 60)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15).offset(Direction.NORTH, 15).offset(Direction.WEST, 15),
                    pos.offset(Direction.NORTH, 1).offset(Direction.WEST, 0)).forEach((pos2) ->
            {
                if (isAir(pos2)&&world.getBlockState(pos2.up()).getBlock() != Blocks.VINE)
                {
					if(random.nextInt(20)==5)
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.get().getDefaultState());
                }
            });
        }
        if (cooldown == 70)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15).offset(Direction.SOUTH, 15).offset(Direction.WEST, 15),
                    pos.offset(Direction.SOUTH, 0).offset(Direction.WEST, 1)).forEach((pos2) ->
            {
                if (isAir(pos2)&&world.getBlockState(pos2.up()).getBlock() != Blocks.VINE)
                {
					if(random.nextInt(20)==5)
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.get().getDefaultState());
                }
            });
        }
        if (cooldown == 80)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15).offset(Direction.NORTH, 15).offset(Direction.EAST, 15),
                    pos.offset(Direction.NORTH, 0).offset(Direction.EAST, 1)).forEach((pos2) ->
            {
                if (isAir(pos2)&&world.getBlockState(pos2.up()).getBlock() != Blocks.VINE)
                {
					if(random.nextInt(20)==5)
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.get().getDefaultState());
                }
            });
        }
        if (cooldown == 90)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15).offset(Direction.SOUTH, 15).offset(Direction.EAST, 15),
                    pos.offset(Direction.SOUTH, 1).offset(Direction.EAST, 0)).forEach((pos2) ->
            {
                if (isAir(pos2)&&world.getBlockState(pos2.up()).getBlock() != Blocks.VINE)
                {
					if(random.nextInt(20)==5)
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.get().getDefaultState());
                }
            });
        }
        if (cooldown == 100)
        {
            BlockPos.getAllInBox(pos.offset(Direction.DOWN, 15),
                    pos.offset(Direction.DOWN, 1)).forEach((pos2) ->
            {
                if (isAir(pos2)&&world.getBlockState(pos2.up()).getBlock() != Blocks.VINE)
                {
					if(random.nextInt(20)==5)
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.get().getDefaultState());
                }
            });
            BlockPos.getAllInBox(pos.offset(Direction.UP, 15),
                    pos.offset(Direction.UP, 1)).forEach((pos2) ->
            {
                if (isAir(pos2)&&world.getBlockState(pos2.up()).getBlock() != Blocks.VINE)
                {
					if(random.nextInt(20)==5)
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.get().getDefaultState());
                }
            });

            /*if (!updated)
            {
                updated = true;
                BlockPos.getAllInBox(pos.offset(Direction.UP, 18).offset(Direction.NORTH,18).offset(Direction.WEST,18), pos.offset(Direction.DOWN,18).offset(Direction.SOUTH,18).offset(Direction.EAST,18)).forEach((pos1) -> {
                    if (isAir(pos1))
                    {
                        world.notifyBlockUpdate(pos1, world.getBlockState(pos1), world.getBlockState(pos1), 3);
                    }
                });
            }*/

            cooldown = 0;
        }
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR;
    }
}