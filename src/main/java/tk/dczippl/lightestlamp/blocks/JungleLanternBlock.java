package tk.dczippl.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

@SuppressWarnings({"NullableProblems", "deprecation"})
public class JungleLanternBlock extends Block implements IWaterLoggable
{
    public JungleLanternBlock(Properties properties)
    {
        super(properties.notSolid());
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 12;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_)
    {
        return Block.makeCuboidShape(0,0,0,0,0,0);
    }

    @Override
    public VoxelShape getRenderShape(BlockState p_196247_1_, IBlockReader p_196247_2_, BlockPos p_196247_3_)
    {
        return Block.makeCuboidShape(4,2,4,12,14,12);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.makeCuboidShape(4,2,4,12,14,12);
    }

    @Override
    public void addInformation(ItemStack p_190948_1_, @Nullable IBlockReader p_190948_2_, List<ITextComponent> text, ITooltipFlag p_190948_4_)
    {
        text.add(new TranslationTextComponent("tooltip.lightestlamp.speed_grow").setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.GRAY))));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder); // Doesn't matter which order they are in, still complains. Removing WATERLOGGED crashes the game.
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}