package dev.prefex.lightestlamp.blocks;

import dev.prefex.lightestlamp.entities.cleaners.OmegaChunkCleanerBlockEntity;
import dev.prefex.lightestlamp.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class OmegaChunkCleanerBlock extends ChunkCleanerBlock
{
    public OmegaChunkCleanerBlock(Properties properties)
    {
        super(properties);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new OmegaChunkCleanerBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pBlockEntityType == ModBlockEntities.OCC_BE ? OmegaChunkCleanerBlockEntity::tick : null;
    }
}