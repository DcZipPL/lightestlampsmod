package tk.dczippl.lightestlamp.items;

import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

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
        tooltip.add(new TranslationTextComponent(this.tooltip).setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.GRAY))));
    }

    @Override
    public boolean isDamageable()
    {
        return true;
    }
}