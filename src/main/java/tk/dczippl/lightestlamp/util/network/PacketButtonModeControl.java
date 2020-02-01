package tk.dczippl.lightestlamp.util.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkEvent;
import tk.dczippl.lightestlamp.init.ModEffect;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeTile;

import java.util.function.Supplier;

public class PacketButtonModeControl
{
    private final BlockPos pos;
    private final int type;

    public PacketButtonModeControl(PacketBuffer buf)
    {
        pos = buf.readBlockPos();
        type = buf.readInt();
    }

    public PacketButtonModeControl(BlockPos pos, int type)
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
            TileEntity te = ctx.get().getSender().world.getServer().getWorld(DimensionType.getById(type)).getTileEntity(pos);
            if (te instanceof GasCentrifugeTile)
            {
                GasCentrifugeTile gte = ((GasCentrifugeTile) te);
                gte.startTicksBeforeDumping();
                if (gte.getLiquidMode()>=2)
                    gte.setLiquidMode(0);
                else
                    gte.setLiquidMode(gte.getLiquidMode()+1);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}