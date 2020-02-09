package tk.dczippl.lightestlamp.tile;

import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

public class ZetaLampTileEntity extends TileEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    public ZetaLampTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public ZetaLampTileEntity()
    {
        super(ModTileEntities.ZETA_TE);
    }

    @Override
    public void tick()
    {
        //TODO: Change this to DELTA
        if (world.isRemote) return;

        cooldown++;

        if (cooldown == 10)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 9).offset(Direction.NORTH, 9).offset(Direction.WEST, 9),
                    pos.offset(Direction.DOWN, 9).offset(Direction.SOUTH, 9).offset(Direction.EAST, 9)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
                }
            });

            cooldown = 0;
        }
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || world.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR;
    }
}