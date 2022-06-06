package tk.dczippl.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import tk.dczippl.lightestlamp.init.ModBlocks;

import javax.annotation.Nullable;

public class BetaLampBlock extends Block
{
    public BetaLampBlock()
    {
        super(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.3f,1));
    }

    @Override
    public boolean hasBlockEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockState state, IBlockReader world)
    {
        return new BetaLampBlockEntity();
    }

    @Override
    public int getHarvestLevel(BlockState p_getHarvestLevel_1_)
    {
        return 0;
    }

    @Override
    public void onPlayerDestroy(IWorld iworld, BlockPos pos, BlockState state)
    {
        World world = (World) iworld;
        BlockPos.getAllInBox(pos.offset(Direction.UP, 2).offset(Direction.NORTH,2).offset(Direction.WEST,2), pos.offset(Direction.DOWN, 2).offset(Direction.SOUTH,2).offset(Direction.EAST,2)).forEach((pos1) -> {
            if (world.getBlockState(pos1).getBlock() == ModBlocks.LIGHT_AIR.get())
            {
                world.setBlockState(pos1, Blocks.AIR.getDefaultState());
            }
        });
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> text, ITooltipFlag flag)
    {
        text.add(new TranslationTextComponent("tooltip.lightestlamp.type.beta").setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.GRAY))));
        text.add(new TranslationTextComponent("tooltip.lightestlamp.inverted").setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.GRAY))));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(POWERED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(POWERED,false);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos)
    {
        return state.get(POWERED) ? 0 : 15;
    }
}