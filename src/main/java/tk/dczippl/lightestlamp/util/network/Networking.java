package tk.dczippl.lightestlamp.util.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import tk.dczippl.lightestlamp.Reference;

import static org.apache.http.params.CoreProtocolPNames.PROTOCOL_VERSION;

public class Networking
{
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID()
    {
        return ID++;
    }

    public static void registerMessages()
    {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Reference.MOD_ID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );
        INSTANCE.registerMessage(nextID(),
                PacketButtonRedstone.class,
                PacketButtonRedstone::toBytes,
                PacketButtonRedstone::new,
                PacketButtonRedstone::handle);
        INSTANCE.registerMessage(nextID(),
                PacketButtonModeControl.class,
                PacketButtonModeControl::toBytes,
                PacketButtonModeControl::new,
                PacketButtonModeControl::handle);
    }
}