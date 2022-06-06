package tk.dczippl.lightestlamp.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

public class ClearLampTileEntity extends BlockEntity implements ITickableTileEntity
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
