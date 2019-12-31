package tk.dczippl.lightestlamp.init;


import javafx.geometry.Side;
//import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import tk.dczippl.lightestlamp.Reference;
//import tk.dczippl.lightestlamp.machine.gasextractor.GasExtractorBlock;

public class ModPacketHandler
{
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Reference.MOD_ID, "main_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void registerMessages(String channelName) {
        registerMessages();
    }

    /**
     * Sends a packet to the server.<br>
     * Must be called Client side.
     */
    /**
     * Sends a packet to the server.<br>
     * Must be called Client side.
     */
    public static void sendToServer(Object msg)
    {
        HANDLER.sendToServer(msg);
    }

    /**
     * Send a packet to a specific player.<br>
     * Must be called Server side.
     */
    public static void sendTo(Object msg, EntityPlayerMP player)
    {
        if (!(player instanceof FakePlayer))
        {
            HANDLER.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void registerMessages() {
        // Register messages which are sent from the client to the server here:
        //HANDLER.registerMessage(0, MSG, GasExtractorBlock.class, Dist.DEDICATED_SERVER);
        //INSTANCE.registerMessage(UpdateTe.Handler.class, UpdateTe.class, nextID(), Side.SERVER);

    }
}
