package tk.dczippl.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ClearLampBlock extends Block
{
    public ClearLampBlock(Properties properties)
    {
        super(properties);
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
        return new ClearLampBlockEntity();
    }

    @Override
    public void addInformation(ItemStack p_190948_1_, @Nullable IBlockReader p_190948_2_, List<ITextComponent> text, ITooltipFlag p_190948_4_)
    {
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