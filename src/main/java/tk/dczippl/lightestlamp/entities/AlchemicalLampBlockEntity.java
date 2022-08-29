package tk.dczippl.lightestlamp.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import tk.dczippl.lightestlamp.init.ModBlockEntities;

import static tk.dczippl.lightestlamp.Main.repelEntitiesInAABBFromPoint;

public class AlchemicalLampBlockEntity extends BlockEntity implements BlockEntityTicker<AlchemicalLampBlockEntity>
{
    public AlchemicalLampBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }
    public AlchemicalLampBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {this(ModBlockEntities.ALCHEMICALLAMP_BE, pWorldPosition, pBlockState);}

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, AlchemicalLampBlockEntity pBlockEntity) {
        repelEntitiesInAABBFromPoint(pLevel, new AABB(pPos.offset(-8, -8, -8), pPos.offset(8, 8, 8)), pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, false);
    }
}
