package dev.prefex.lightestlamp.entities;

import dev.prefex.lightestlamp.init.ModBlockEntities;
import dev.prefex.lightestlamp.lighting.LampLightManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

// TODO: move to NormalLampBlock
public class OmegaLampBlockEntity extends BlockEntity {
    public OmegaLampBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public OmegaLampBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntities.OMEGA_BE.get(), pos, state);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level instanceof ServerLevel serverLevel) {
            LampLightManager.register(serverLevel, worldPosition, getBlockState());
        }
    }
}
