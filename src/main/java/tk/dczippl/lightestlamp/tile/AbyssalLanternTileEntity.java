package tk.dczippl.lightestlamp.tile;

import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModTileEntities;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class AbyssalLanternTileEntity extends TileEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    //Delta
    public AbyssalLanternTileEntity(TileEntityType<?> type)
    {
        super(type);
    }

    public AbyssalLanternTileEntity()
    {
        super(ModTileEntities.ABYSSALLANTERN_TE);
    }

    @Override
    public void tick()
    {
        //TODO: Change this to DELTA
        if (world.isRemote) return;

        cooldown++;

        if (cooldown == 5)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 5).offset(Direction.NORTH, 5).offset(Direction.WEST, 5),
                    pos.offset(Direction.DOWN, 5).offset(Direction.SOUTH, 5).offset(Direction.EAST, 5)).forEach((pos2) ->
            {
                if (isAir(pos2))
                {
                    world.setBlockState(pos2, ModBlocks.WATERLOGGABLE_LIGHT_AIR.getDefaultState().with(WATERLOGGED,false));
                }
                else if (world.getBlockState(pos2).getBlock() == Blocks.WATER)
                {
                    world.setBlockState(pos2, ModBlocks.WATERLOGGABLE_LIGHT_AIR.getDefaultState().with(WATERLOGGED,true));
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