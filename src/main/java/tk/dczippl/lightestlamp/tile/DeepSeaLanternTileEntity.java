package tk.dczippl.lightestlamp.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

public class DeepSeaLanternTileEntity extends BlockEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    //Beta
    public DeepSeaLanternTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public DeepSeaLanternTileEntity()
    {
        super(ModTileEntities.DEEPSEALANTERN_TE);
    }

    @Override
    public void tick()
    {
        if (world.isRemote) return;

        cooldown++;

        if (cooldown == 5)
        {
            BlockPos pos1 = pos.offset(Direction.UP, 2);
            if (isAir(pos1))
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,false));
            }
            else if (world.getBlockState(pos1).getBlock() == Blocks.WATER)
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,true));
            }

            pos1 = pos.offset(Direction.DOWN, 2);
            if (isAir(pos1))
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,false));
            }
            else if (world.getBlockState(pos1).getBlock() == Blocks.WATER)
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,true));
            }

            pos1 = pos.offset(Direction.NORTH, 2);
            if (isAir(pos1))
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,false));
            }
            else if (world.getBlockState(pos1).getBlock() == Blocks.WATER)
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,true));
            }

            pos1 = pos.offset(Direction.SOUTH, 2);
            if (isAir(pos1))
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,false));
            }
            else if (world.getBlockState(pos1).getBlock() == Blocks.WATER)
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,true));
            }

            pos1 = pos.offset(Direction.WEST, 2);
            if (isAir(pos1))
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,false));
            }
            else if (world.getBlockState(pos1).getBlock() == Blocks.WATER)
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,true));
            }

            pos1 = pos.offset(Direction.EAST, 2);
            if (isAir(pos1))
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,false));
            }
            else if (world.getBlockState(pos1).getBlock() == Blocks.WATER)
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,true));
            }

            BlockPos.getAllInBox(pos.offset(Direction.UP, 1).offset(Direction.NORTH, 1).offset(Direction.WEST, 1), pos.offset(Direction.DOWN, 1).offset(Direction.SOUTH, 1).offset(Direction.EAST, 1)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,false));
                }
                else if (world.getBlockState(pos2).getBlock() == Blocks.WATER)
                {
                    world.setBlockState(pos2, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,true));
                }
            });

            cooldown = 0;
        }
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || world.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR.get();
    }
}