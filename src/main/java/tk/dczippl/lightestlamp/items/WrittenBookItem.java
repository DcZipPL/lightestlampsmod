package tk.dczippl.lightestlamp.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
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

/*    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        if (!world.isRemote() && player instanceof EntityPlayerMP)
        {
            NetworkHooks.openGui((EntityPlayerMP) player,null, packetBuffer -> packetBuffer.writeBlockPos(player.getPosition()));
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> text, ITooltipFlag p_77624_4_)
    {
        text.add(new TextComponentTranslation("tooltip.lamp.title").applyTextStyle(TextFormatting.GRAY));
        text.add(new TextComponentTranslation("tooltip.lamp.wip").applyTextStyle(TextFormatting.RED));
    }*/
}