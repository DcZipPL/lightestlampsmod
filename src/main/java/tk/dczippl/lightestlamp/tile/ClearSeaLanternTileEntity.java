package tk.dczippl.lightestlamp.tile;

import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class ClearSeaLanternTileEntity extends TileEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    public ClearSeaLanternTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public ClearSeaLanternTileEntity()
    {
        super(ModTileEntities.CLEARSEALANTERN_TE);
    }

    @Override
    public void tick()
    {
        if (world.isRemote) return;

        cooldown++;

        if (cooldown == 5)
        {
            BlockPos pos1 = pos.offset(Direction.UP);
            if (isAir(pos1))
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.getDefaultState().with(WATERLOGGED,false));
            }
            else if (world.getBlockState(pos1).getBlock() == Blocks.WATER)
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.getDefaultState().with(WATERLOGGED,true));
            }

            pos1 = pos.offset(Direction.DOWN);
            if (isAir(pos1))
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.getDefaultState().with(WATERLOGGED,false));
            }
            else if (world.getBlockState(pos1).getBlock() == Blocks.WATER)
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.getDefaultState().with(WATERLOGGED,true));
            }

            pos1 = pos.offset(Direction.NORTH);
            if (isAir(pos1))
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.getDefaultState().with(WATERLOGGED,false));
            }
            else if (world.getBlockState(pos1).getBlock() == Blocks.WATER)
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.getDefaultState().with(WATERLOGGED,true));
            }

            pos1 = pos.offset(Direction.SOUTH);
            if (isAir(pos1))
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.getDefaultState().with(WATERLOGGED,false));
            }
            else if (world.getBlockState(pos1).getBlock() == Blocks.WATER)
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.getDefaultState().with(WATERLOGGED,true));
            }

            pos1 = pos.offset(Direction.WEST);
            if (isAir(pos1))
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.getDefaultState().with(WATERLOGGED,false));
            }
            else if (world.getBlockState(pos1).getBlock() == Blocks.WATER)
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.getDefaultState().with(WATERLOGGED,true));
            }

            pos1 = pos.offset(Direction.EAST);
            if (isAir(pos1))
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.getDefaultState().with(WATERLOGGED,false));
            }
            else if (world.getBlockState(pos1).getBlock() == Blocks.WATER)
            {
                world.setBlockState(pos1, ModBlocks.WATERLOGGABLE_LIGHT_AIR.getDefaultState().with(WATERLOGGED,true));
            }

            cooldown = 0;
        }
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || world.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR;
    }
}