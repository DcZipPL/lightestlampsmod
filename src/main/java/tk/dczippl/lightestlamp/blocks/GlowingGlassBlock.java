package tk.dczippl.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;

public class GlowingGlassBlock extends GlassBlock
{
    public GlowingGlassBlock()
    {
        super(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.3f,1).notSolid());
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 14;
    }

    @Override
    public int getHarvestLevel(BlockState p_getHarvestLevel_1_)
    {
        return 0;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> text, ITooltipFlag flag)
    {
        text.add(new TranslationTextComponent("tooltip.lightestlamp.inverted").setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.GRAY))));
    }
}