package tk.dczippl.lightestlamp.tile;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import tk.dczippl.lightestlamp.init.ModTileEntities;

import java.util.List;

import static tk.dczippl.lightestlamp.Main.repelEntitiesInAABBFromPoint;

public class AlchemicalLampTileEntity extends TileEntity implements ITickableTileEntity
{
    public AlchemicalLampTileEntity()
    {
        super(ModTileEntities.ALCHEMICALLAMP_TE);
    }

    public AlchemicalLampTileEntity(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick()
    {
        repelEntitiesInAABBFromPoint(world, new AxisAlignedBB(pos.add(-8, -8, -8), pos.add(8, 8, 8)), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, false);
    }
}
