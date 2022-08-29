package dev.prefex.lightestlamp.entities.cleaners;

import dev.prefex.lightestlamp.init.ModBlockEntities;
import dev.prefex.lightestlamp.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ChunkCleanerBlockEntity extends BlockEntity
{
    public ChunkCleanerBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }
    public ChunkCleanerBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {this(ModBlockEntities.CLEANER_BE, pWorldPosition, pBlockState);}

    private boolean isAir(BlockPos pos)
    {
        return level.getBlockState(pos).getBlock() == Blocks.AIR || level.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || level.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR.get();
    }

    public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState pState, BlockEntity pBlockEntity) {
        if (pLevel.isClientSide) return;
        BlockPos.betweenClosed(
                pPos.above(18).north(18).west(18),
                pPos.below(18).south(18).east(18))
                .forEach((pos1) -> {
            if (((ChunkCleanerBlockEntity) pBlockEntity).level.getBlockState(pos1).getBlock() == ModBlocks.LIGHT_AIR.get())
            {
                ((ChunkCleanerBlockEntity) pBlockEntity).level.setBlockAndUpdate(pos1, Blocks.AIR.defaultBlockState());
            }
            else if (pLevel.getBlockState(pos1).getBlock() == ModBlocks.WATERLOGGABLE_LIGHT_AIR.get() && !pLevel.getBlockState(pos1).getValue(BlockStateProperties.WATERLOGGED))
            {
                ((ChunkCleanerBlockEntity) pBlockEntity).level.setBlockAndUpdate(pos1, Blocks.AIR.defaultBlockState());
            }
            else if (pLevel.getBlockState(pos1).getBlock() == ModBlocks.WATERLOGGABLE_LIGHT_AIR.get() && pLevel.getBlockState(pos1).getValue(BlockStateProperties.WATERLOGGED))
            {
                ((ChunkCleanerBlockEntity) pBlockEntity).level.setBlockAndUpdate(pos1, Blocks.WATER.defaultBlockState());
            }
        });
        ((ChunkCleanerBlockEntity) pBlockEntity).level.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
        //Recalc Light in nearby chunks
    }
}