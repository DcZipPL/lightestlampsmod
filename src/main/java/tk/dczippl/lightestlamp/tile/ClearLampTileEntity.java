package tk.dczippl.lightestlamp.tile;

import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import tk.dczippl.lightestlamp.init.ModTileEntities;

import static net.minecraft.state.properties.BlockStateProperties.POWERED;

public class ClearLampTileEntity extends TileEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    public ClearLampTileEntity(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }

    public ClearLampTileEntity()
    {
        super(ModTileEntities.CLEAR_TE);
    }

    @Override
    public void tick()
    {
        if (world.isRemote) return;
        if (cooldown>=4)
        {
            if (world.getRedstonePowerFromNeighbors(pos) > 0)
            {
                if (!getBlockState().get(POWERED))
                    world.setBlockState(pos, getBlockState().with(POWERED,true));
            }
            else
            {
                if (getBlockState().get(POWERED))
                    world.setBlockState(pos, getBlockState().with(POWERED,false));
            }
            cooldown=0;
        }
        else
            cooldown++;
    }
}
