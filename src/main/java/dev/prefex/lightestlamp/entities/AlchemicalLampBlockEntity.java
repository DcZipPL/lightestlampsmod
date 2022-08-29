package dev.prefex.lightestlamp.entities;

import dev.prefex.lightestlamp.Main;
import dev.prefex.lightestlamp.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class AlchemicalLampBlockEntity extends BlockEntity
{
    public AlchemicalLampBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }
    public AlchemicalLampBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {this(ModBlockEntities.ALCHEMICALLAMP_BE.get(), pWorldPosition, pBlockState);}

    public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
        Main.repelEntitiesInAABBFromPoint(pLevel, new AABB(pPos.offset(-8, -8, -8), pPos.offset(8, 8, 8)), pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, false);
    }
}
