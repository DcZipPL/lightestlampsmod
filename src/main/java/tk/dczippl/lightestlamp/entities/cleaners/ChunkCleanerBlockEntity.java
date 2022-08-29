package tk.dczippl.lightestlamp.entities.cleaners;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModBlockEntities;

public class ChunkCleanerBlockEntity extends BlockEntity implements BlockEntityTicker<ChunkCleanerBlockEntity>
{
    public ChunkCleanerBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }
    public ChunkCleanerBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {this(ModBlockEntities.CLEANER_BE, pWorldPosition, pBlockState);}

    private boolean isAir(BlockPos pos)
    {
        return level.getBlockState(pos).getBlock() == Blocks.AIR || level.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || level.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR.get();
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, ChunkCleanerBlockEntity pBlockEntity) {
        if (pLevel.isClientSide) return;
        BlockPos.betweenClosed(
                pPos.above(18).north(18).west(18),
                pPos.below(18).south(18).east(18))
                .forEach((pos1) -> {
            if (level.getBlockState(pos1).getBlock() == ModBlocks.LIGHT_AIR.get())
            {
                level.setBlockAndUpdate(pos1, Blocks.AIR.defaultBlockState());
            }
            else if (pLevel.getBlockState(pos1).getBlock() == ModBlocks.WATERLOGGABLE_LIGHT_AIR.get() && !pLevel.getBlockState(pos1).getValue(BlockStateProperties.WATERLOGGED))
            {
                level.setBlockAndUpdate(pos1, Blocks.AIR.defaultBlockState());
            }
            else if (pLevel.getBlockState(pos1).getBlock() == ModBlocks.WATERLOGGABLE_LIGHT_AIR.get() && pLevel.getBlockState(pos1).getValue(BlockStateProperties.WATERLOGGED))
            {
                level.setBlockAndUpdate(pos1, Blocks.WATER.defaultBlockState());
            }
        });
        level.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
        //Recalc Light in nearby chunks
    }
}