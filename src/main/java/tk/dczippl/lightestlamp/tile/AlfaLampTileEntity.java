package tk.dczippl.lightestlamp.tile;

import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.blocks.LightAirBlock;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.POWERED;

public class AlfaLampTileEntity extends TileEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    public AlfaLampTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public AlfaLampTileEntity()
    {
        super(ModTileEntities.ALFA_TE);
    }

    @Override
    public void tick()
    {
        if (world.isRemote) return;
        //world.getChunk(pos)

        cooldown++;

        if (cooldown == 5)
        {
            if (world.getRedstonePowerFromNeighbors(pos) > 0)
            {
                if (!getBlockState().get(POWERED))
                {
                    world.setBlockState(pos, getBlockState().with(POWERED, true));

                    BlockPos.getAllInBox(pos.offset(Direction.UP, 1).offset(Direction.NORTH, 1).offset(Direction.WEST, 1), pos.offset(Direction.DOWN, 1).offset(Direction.SOUTH, 1).offset(Direction.EAST, 1)).forEach((pos1) ->
                    {
                        if (world.getBlockState(pos1).getBlock() == ModBlocks.LIGHT_AIR)
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
            }
            cooldown = 0;
        }
    }
}