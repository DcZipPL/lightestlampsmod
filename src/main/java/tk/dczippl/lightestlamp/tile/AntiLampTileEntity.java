package tk.dczippl.lightestlamp.tile;

import net.minecraft.world.level.block.entity.BlockEntity;
import tk.dczippl.lightestlamp.init.ModTileEntities;

public class AntiLampTileEntity extends BlockEntity implements ITickableTileEntity
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