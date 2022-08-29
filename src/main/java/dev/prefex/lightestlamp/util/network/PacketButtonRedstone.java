package dev.prefex.lightestlamp.util.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeBlockEntity;

import java.util.function.Supplier;

public class PacketButtonRedstone
{
    private final BlockPos pos;
    private final int type;

    public PacketButtonRedstone(FriendlyByteBuf buf)
    {
        pos = buf.readBlockPos();
        type = buf.readInt();
    }

    public PacketButtonRedstone(BlockPos pos, int type)
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
            BlockEntity be = ctx.get().getSender().getLevel().getBlockEntity(pos);
            if (be instanceof GasCentrifugeBlockEntity)
            {
                GasCentrifugeBlockEntity gbe = ((GasCentrifugeBlockEntity) be);
                if (gbe.getRedstoneMode()>=2)
                    gbe.setRedstoneMode(0);
                else
                    gbe.setRedstoneMode(gbe.getRedstoneMode()+1);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}