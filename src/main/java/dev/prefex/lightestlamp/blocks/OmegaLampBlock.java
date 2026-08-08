package dev.prefex.lightestlamp.blocks;

import com.mojang.serialization.MapCodec;
import dev.prefex.lightestlamp.entities.OmegaLampBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

// TODO: Move to normal lamp block
public class OmegaLampBlock extends LightestLampBlock {
    private final MapCodec<? extends BaseEntityBlock> codec = MapCodec.unit(this);

    public OmegaLampBlock() {
        this(false);
    }

    public OmegaLampBlock(boolean luminant) {
        super(Type.NORMAL, Tier.OMEGA, luminant);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return codec;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OmegaLampBlockEntity(pos, state);
    }
}
