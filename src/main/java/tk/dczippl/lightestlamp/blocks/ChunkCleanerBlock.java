package tk.dczippl.lightestlamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import tk.dczippl.lightestlamp.tile.LightAirTileEntity;

import javax.annotation.Nullable;

public class ChunkCleanerBlock extends Block
{
    public ChunkCleanerBlock(Properties properties)
    {
        super(properties.hardnessAndResistance(-1,-1));
    }

    @Override
    public boolean canEntityDestroy(BlockState state, IBlockReader world, BlockPos pos, Entity entity)
    {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new LightAirTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader world, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean isAir(BlockState p_196261_1_) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState p_149645_1_)
    {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_)
    {
        return Block.makeCuboidShape(0,0,0,0,0,0);
    }
}