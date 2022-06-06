package tk.dczippl.lightestlamp.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

public class DeepOceanLanternTileEntity extends BlockEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    //Delta
    public DeepOceanLanternTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public DeepOceanLanternTileEntity()
    {
        super(ModTileEntities.DEEPOCEANLANTERN_TE);
    }

    @Override
    public void tick()
    {
        //TODO: Change this to DELTA
        if (world.isRemote) return;

        cooldown++;

        if (cooldown == 5)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 4).offset(Direction.NORTH, 4).offset(Direction.WEST, 4),
                    pos.offset(Direction.DOWN, 4).offset(Direction.SOUTH, 4).offset(Direction.EAST, 4)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,false));
                }
                else if (world.getBlockState(pos2).getBlock() == Blocks.WATER)
                {
                    world.setBlockState(pos2, ModBlocks.WATERLOGGABLE_LIGHT_AIR.get().getDefaultState().with(WATERLOGGED,true));
                }
            });

            cooldown = 0;
        }
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || world.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR.get();
    }
}