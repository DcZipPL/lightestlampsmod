package dev.prefex.lightestlamp.util.network;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketButtonRedstone(BlockPos pos) implements CustomPacketPayload {
    public static final Type<PacketButtonRedstone> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(Util.MOD_ID, "button_redstone"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketButtonRedstone> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, PacketButtonRedstone::pos, PacketButtonRedstone::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    static void handle(PacketButtonRedstone payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)
                || !player.level().hasChunkAt(payload.pos)
                || !payload.pos.closerToCenterThan(player.position(), 8.0)) {
            return;
        }

        if (player.level().getBlockEntity(payload.pos) instanceof GasCentrifugeBlockEntity blockEntity) {
            blockEntity.setRedstoneMode((blockEntity.getRedstoneMode() + 1) % 3);
            blockEntity.setChanged();
        }
    }
}
