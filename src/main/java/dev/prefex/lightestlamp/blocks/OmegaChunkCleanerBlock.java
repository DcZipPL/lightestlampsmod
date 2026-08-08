package dev.prefex.lightestlamp.blocks;

import com.mojang.serialization.MapCodec;
import dev.prefex.lightestlamp.entities.cleaners.OmegaChunkCleanerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OmegaChunkCleanerBlock extends ChunkCleanerBlock
{
    public static final MapCodec<OmegaChunkCleanerBlock> CODEC = simpleCodec(OmegaChunkCleanerBlock::new);

    public OmegaChunkCleanerBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected MapCodec<? extends OmegaChunkCleanerBlock> codec() {
        return CODEC;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new OmegaChunkCleanerBlockEntity(pPos, pState);
    }

    // OCC does not have tick anymore. LampLightManager removes them during chunk reconciliation, legacy block.
}
