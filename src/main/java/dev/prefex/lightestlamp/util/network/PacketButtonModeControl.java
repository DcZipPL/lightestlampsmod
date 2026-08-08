package dev.prefex.lightestlamp.util.network;

import dev.prefex.lightestlamp.Config;
import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketButtonModeControl(BlockPos pos, int mode) implements CustomPacketPayload {
    public static final Type<PacketButtonModeControl> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(Util.MOD_ID, "button_mode"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketButtonModeControl> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, PacketButtonModeControl::pos,
            ByteBufCodecs.VAR_INT, PacketButtonModeControl::mode,
            PacketButtonModeControl::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    static void handle(PacketButtonModeControl payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)
                || !player.level().hasChunkAt(payload.pos)
                || !payload.pos.closerToCenterThan(player.position(), 8.0)
                || payload.mode < 0 || payload.mode > 2) {
            return;
        }

        if (player.level().getBlockEntity(payload.pos) instanceof GasCentrifugeBlockEntity blockEntity
                && isAllowed(payload.mode)) {
            blockEntity.setLiquidMode(payload.mode);
            blockEntity.setChanged();
        }
    }

    private static boolean isAllowed(int mode) {
        return switch (Config.ENERGY_MODE.get()) {
            case passive_only -> mode == 0;
            case energy_only -> mode != 0;
            case no_overclocking -> mode == 1;
            case no_overclocking_with_passive -> mode != 2;
            case both -> true;
        };
    }
}
