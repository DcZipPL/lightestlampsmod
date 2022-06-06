package tk.dczippl.lightestlamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class WaterLoggableLightAirBlock extends Block implements IWaterLoggable
{
    public WaterLoggableLightAirBlock() {
        super(Block.Properties.create(Material.AIR).notSolid());
        this.setDefaultState(this.stateContainer.getBaseState().with(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true)));
    }

    VoxelShape rs = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return rs;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return rs;
    }

    @Override
    public BlockRenderType getRenderType(BlockState p_149645_1_)
    {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public boolean canStickTo(BlockState state, BlockState other) {
        return false;
    }

    @Override
    public boolean canSpawnInBlock() {
        return true;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side)
    {
        return false;
    }

    @Override
    public boolean canBeReplacedByLeaves(BlockState state, IWorldReader world, BlockPos pos)
    {
        return true;
    }

    @Override
    public boolean canBeReplacedByLogs(BlockState state, IWorldReader world, BlockPos pos)
    {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder); // Doesn't matter which order they are in, still complains. Removing WATERLOGGED crashes the game.
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(BlockStateProperties.WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}
