package tk.dczippl.lightestlamp.items;

import al132.chemlib.ChemLib;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ControlledItem extends Item
{
    public ControlledItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        /*try
        {
            if (ChemLib.MODID!=null)
                tooltip.add(new TranslationTextComponent("tooltip.lightestlamp.chemlib.detected").applyTextStyle(TextFormatting.RED));
        }
        catch (NoClassDefFoundError ignore){}*/
    }
}