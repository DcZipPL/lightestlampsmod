package tk.dczippl.lightestlamp.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tk.dczippl.lightestlamp.init.ModBlockEntities;

import static tk.dczippl.lightestlamp.Main.repelEntitiesInAABBFromPoint;

public class AlchemicalLampBlockEntity extends BlockEntity implements BlockEntityTicker<AlchemicalLampBlockEntity>
{
    public AlchemicalLampBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }
    public AlchemicalLampBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {this(ModBlockEntities.ALCHEMICALLAMP_TE, pWorldPosition, pBlockState);}

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, AlchemicalLampBlockEntity pBlockEntity) {
        repelEntitiesInAABBFromPoint(pLevel, new AxisAlignedBB(pPos.offset(-8, -8, -8), pPos.offset(8, 8, 8)), pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, false);
    }
}
