package tk.dczippl.lightestlamp.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConfirmOpenLinkScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class WrittenBookItem extends Item
{
    public WrittenBookItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (!worldIn.isRemote)
            Minecraft.getInstance().player.sendChatMessage("Lightest Lamps Wiki: https://github.com/DcZipPL/lightestlampsmod/wiki");
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> text, ITooltipFlag p_77624_4_)
    {
        text.add(new TranslationTextComponent("tooltip.lightestlamp.book.title0").applyTextStyle(TextFormatting.GRAY));
        text.add(new TranslationTextComponent("tooltip.lightestlamp.book.title1").applyTextStyle(TextFormatting.GRAY));
        text.add(new TranslationTextComponent("tooltip.lightestlamp.book.title2").applyTextStyle(TextFormatting.GRAY));
        text.add(new TranslationTextComponent("tooltip.lightestlamp.book.title3").applyTextStyle(TextFormatting.GRAY));
        text.add(new TranslationTextComponent("tooltip.lightestlamp.book.title4").applyTextStyle(TextFormatting.GRAY));
    }
}