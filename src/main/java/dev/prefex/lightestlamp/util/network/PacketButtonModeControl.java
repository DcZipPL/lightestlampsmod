package dev.prefex.lightestlamp.util.network;

import dev.prefex.lightestlamp.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeBlockEntity;

import java.util.function.Supplier;

public class PacketButtonModeControl
{
    private final BlockPos pos;
    private final int type;

    public PacketButtonModeControl(FriendlyByteBuf buf)
    {
        pos = buf.readBlockPos();
        type = buf.readInt();
    }

    public PacketButtonModeControl(BlockPos pos, int type)
    {
        this.pos = pos;
        this.type = type;
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(pos);
        buf.writeInt(type);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            BlockEntity be = ctx.get().getSender().getCommandSenderWorld().getBlockEntity(pos);
            if (be instanceof GasCentrifugeBlockEntity)
            {
                GasCentrifugeBlockEntity gbe = ((GasCentrifugeBlockEntity) be);
                if (gbe.getLiquidMode() >= ((Config.ENERGY_MODE.get() == Config.EnergyModes.no_overclocking_with_passive
                        || Config.ENERGY_MODE.get() == Config.EnergyModes.no_overclocking)
                        ? (Config.ENERGY_MODE.get() == Config.EnergyModes.passive_only) ? 0 : 1 : 2)) {
                    gbe.setLiquidMode((Config.ENERGY_MODE.get() == Config.EnergyModes.energy_only
                            || Config.ENERGY_MODE.get() == Config.EnergyModes.no_overclocking)
                            ? 1 : 0
                    );
                } else {
                    gbe.setLiquidMode(gbe.getLiquidMode() + 1);
                }
                if (gbe.getLiquidMode() == 2){ // TODO: Save this change in GasCentrifugeBlockEntity
                    gbe.getEnergyStorage().changeTransferRate(GasCentrifugeBlockEntity.OC_MAX_IN);
                }else{
                    gbe.getEnergyStorage().changeTransferRate(GasCentrifugeBlockEntity.MAX_IN);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}