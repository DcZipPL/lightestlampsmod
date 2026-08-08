package dev.prefex.lightestlamp.util.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class Networking {
    private static final String PROTOCOL_VERSION = "1";

    private Networking() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(PROTOCOL_VERSION);
        registrar.playToServer(PacketButtonRedstone.TYPE, PacketButtonRedstone.STREAM_CODEC, PacketButtonRedstone::handle);
        registrar.playToServer(PacketButtonModeControl.TYPE, PacketButtonModeControl.STREAM_CODEC, PacketButtonModeControl::handle);
    }
}
