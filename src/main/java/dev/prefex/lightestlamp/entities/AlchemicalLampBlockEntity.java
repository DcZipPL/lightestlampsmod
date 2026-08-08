package dev.prefex.lightestlamp.entities;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class AlchemicalLampBlockEntity extends BlockEntity
{
    private final AABB effectBounds;

    public AlchemicalLampBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
        this.effectBounds = new AABB(pWorldPosition).inflate(8.0D);
    }
    public AlchemicalLampBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {this(ModBlockEntities.ALCHEMICALLAMP_BE.get(), pWorldPosition, pBlockState);}

    public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
        if (pLevel.isClientSide || !(pBlockEntity instanceof AlchemicalLampBlockEntity lamp)) {
            return;
        }
        Util.repelEntitiesInAABBFromPoint(pLevel, lamp.effectBounds,
                pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, false);
    }
}
