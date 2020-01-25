package tk.dczippl.lightestlamp.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class FilterItem extends Item
{
    private String tooltip;

    public FilterItem(Properties properties,String tooltip)
    {
        super(properties);
        this.tooltip = tooltip;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(new TranslationTextComponent(this.tooltip).applyTextStyle(TextFormatting.GRAY));
    }

    @Override
    public boolean isDamageable()
    {
        return true;
    }
}