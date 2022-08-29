package tk.dczippl.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import tk.dczippl.lightestlamp.entities.cleaners.OmegaChunkCleanerBlockEntity;

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
}