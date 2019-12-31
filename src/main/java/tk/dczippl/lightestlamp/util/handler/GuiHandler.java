package tk.dczippl.lightestlamp.util.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import tk.dczippl.lightestlamp.Main;

public class GuiHandler
{
    /*public static GuiScreen getClientGuiElement(FMLPlayMessages.OpenContainer message)
    {
        PacketBuffer buf = message.getAdditionalData();
        BlockPos pos = buf.readBlockPos();
        Main.LOGGER.info("getClientGuiElement: " + message.getId() + " " + pos);

        World world = Minecraft.getInstance().world;
        PlayerEntity entityPlayer = Minecraft.getInstance().player;
        TileEntity te = world.getTileEntity(pos);

        if (te instanceof GasExtractorTileEntity)
            return new GasExtractorGui((GasExtractorTileEntity) te, new GasExtractorContainer(entityPlayer.inventory, (GasExtractorTileEntity) te));
        else
            return null;
    }*/
}