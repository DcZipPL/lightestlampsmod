package tk.dczippl.lightestlamp.tile;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.LightType;
import tk.dczippl.lightestlamp.init.ModTileEntities;

import static tk.dczippl.lightestlamp.Main.repelEntitiesInAABBFromPoint;

public class AntiLampTileEntity extends TileEntity implements ITickableTileEntity
{
    public AntiLampTileEntity()
    {
        super(ModTileEntities.ANTILAMP_TE);
    }

    @Override
    public void tick()
    {
        //world.setLightFor();
    }
}