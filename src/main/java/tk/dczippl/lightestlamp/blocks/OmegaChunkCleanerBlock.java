package tk.dczippl.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class OmegaChunkCleanerBlock extends Block
{
    public OmegaChunkCleanerBlock(Properties properties)
    {
        super(properties.hardnessAndResistance(-1,-1).notSolid());
    }

    @Override
    public boolean canEntityDestroy(BlockState state, IBlockReader world, BlockPos pos, Entity entity)
    {
        return false;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockState state, IBlockReader world)
    {
        return new OmegaChunkCleanerBlockEntity();
    }

    @Override
    public boolean hasBlockEntity(BlockState state)
    {
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

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(0,0,0,0,0,0);
    }
}