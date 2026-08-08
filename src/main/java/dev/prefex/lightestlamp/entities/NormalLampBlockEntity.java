package dev.prefex.lightestlamp.entities;

import dev.prefex.lightestlamp.init.ModBlockEntities;
import dev.prefex.lightestlamp.lighting.LampLightManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class NormalLampBlockEntity extends BlockEntity {
    public NormalLampBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public NormalLampBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntities.NORMAL_LAMP_BE.get(), pos, state);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level instanceof ServerLevel serverLevel) {
            LampLightManager.register(serverLevel, worldPosition, getBlockState());
        }
    }
}
