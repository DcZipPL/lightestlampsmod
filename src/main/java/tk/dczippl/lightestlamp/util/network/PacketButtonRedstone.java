package tk.dczippl.lightestlamp.util.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeTile;

import java.util.function.Supplier;

public class PacketButtonRedstone
{
    private final BlockPos pos;
    private final int type;

    public PacketButtonRedstone(PacketBuffer buf)
    {
        pos = buf.readBlockPos();
        type = buf.readInt();
    }

    public PacketButtonRedstone(BlockPos pos, int type)
    {
        this.pos = pos;
        this.type = type;
    }

    public void toBytes(PacketBuffer buf)
    {
        buf.writeBlockPos(pos);
        buf.writeInt(type);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            TileEntity te = ctx.get().getSender().getServerWorld().getTileEntity(pos);
            if (te instanceof GasCentrifugeTile)
            {
                GasCentrifugeTile gte = ((GasCentrifugeTile) te);
                if (gte.getRedstoneMode()>=2)
                    gte.setRedstoneMode(0);
                else
                    gte.setRedstoneMode(gte.getRedstoneMode()+1);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}