package tk.dczippl.lightestlamp.tile.cleaners;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModBlockEntities;

public class ChunkCleanerBlockEntity extends BlockEntity implements ITickableTileEntity
{
    private int cooldown = 0;

    public ChunkCleanerBlockEntity(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }

    public ChunkCleanerBlockEntity()
    {
        super(ModBlockEntities.AIR_TE);
    }

    @Override
    public void tick()
    {
        if (world.isRemote) return;
        if (cooldown >= 1)
        {
            BlockPos.getAllInBox(pos.offset(Direction.UP, 18).offset(Direction.NORTH,18).offset(Direction.WEST,18), pos.offset(Direction.DOWN,18).offset(Direction.SOUTH,18).offset(Direction.EAST,18)).forEach((pos1) -> {
                if (world.getBlockState(pos1).getBlock() == ModBlocks.LIGHT_AIR.get())
                {
                    world.setBlockState(pos1, Blocks.AIR.getDefaultState());
                }
                else if (world.getBlockState(pos1).getBlock() == ModBlocks.WATERLOGGABLE_LIGHT_AIR.get() && !world.getBlockState(pos1).get(BlockStateProperties.WATERLOGGED))
                {
                    world.setBlockState(pos1, Blocks.AIR.getDefaultState());
                }
                else if (world.getBlockState(pos1).getBlock() == ModBlocks.WATERLOGGABLE_LIGHT_AIR.get() && world.getBlockState(pos1).get(BlockStateProperties.WATERLOGGED))
                {
                    world.setBlockState(pos1, Blocks.WATER.getDefaultState());
                }
            });
            /*BlockPos.getAllInBox(pos.offset(Direction.UP, 19).offset(Direction.NORTH,19).offset(Direction.WEST,19), pos.offset(Direction.DOWN,19).offset(Direction.SOUTH,19).offset(Direction.EAST,19)).forEach((pos1) -> {
                if (isAir(pos1))
                    world.notifyBlockUpdate(pos1,world.getBlockState(pos1),world.getBlockState(pos1),3);
            });*/
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.setTileEntity(pos, null);
            //Recalc Light in nearby chunks
        }
        cooldown++;
    }

    private boolean isAir(BlockPos pos)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || world.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR.get();
    }
}