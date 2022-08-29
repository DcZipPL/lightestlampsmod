package tk.dczippl.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import tk.dczippl.lightestlamp.entities.cleaners.ChunkCleanerBlockEntity;
import tk.dczippl.lightestlamp.entities.cleaners.OmegaChunkCleanerBlockEntity;

import javax.annotation.Nullable;

public class ChunkCleanerBlock extends BaseEntityBlock
{
    public ChunkCleanerBlock(Properties properties)
    {
        super(properties.strength(-1,-1).noCollission().noCollission());
    }

    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        return false;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ChunkCleanerBlockEntity(pPos, pState);
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }
}