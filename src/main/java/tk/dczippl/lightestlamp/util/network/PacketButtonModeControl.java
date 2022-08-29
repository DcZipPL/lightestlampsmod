package tk.dczippl.lightestlamp.util.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeBlockEntity;

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
            BlockEntity be = ctx.get().getSender().getLevel().getBlockEntity(pos);
            if (be instanceof GasCentrifugeBlockEntity)
            {
                GasCentrifugeBlockEntity gbe = ((GasCentrifugeBlockEntity) be);
                gbe.startTicksBeforeDumping();
                if (gbe.getLiquidMode()>=2)
                    gbe.setLiquidMode(0);
                else
                    gbe.setLiquidMode(gbe.getLiquidMode()+1);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}