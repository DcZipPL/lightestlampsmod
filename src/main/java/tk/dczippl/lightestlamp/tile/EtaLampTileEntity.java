package tk.dczippl.lightestlamp.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

public class EtaLampTileEntity extends BlockEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    public EtaLampTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public EtaLampTileEntity()
    {
        super(ModTileEntities.ETA_TE);
    }

    @Override
    public void tick()
    {
        //TODO: Change this to DELTA
        if (world.isRemote) return;

        cooldown++;

        if (cooldown == 14)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 11).offset(Direction.NORTH, 11).offset(Direction.WEST, 11),
                    pos.offset(Direction.DOWN, 11).offset(Direction.SOUTH, 11).offset(Direction.EAST, 11)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.LIGHT_AIR.get().getDefaultState());
                }
            });

            cooldown = 0;
        }
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR;
    }
}