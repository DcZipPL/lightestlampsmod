package dev.prefex.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

@SuppressWarnings({"NullableProblems", "deprecation"})
public class LightAirBlock extends AirBlock
{
    public LightAirBlock()
    {
        super(Block.Properties.copy(Blocks.AIR));
    }

    @Override
    public boolean canStickTo(BlockState state, BlockState other) {
        return false;
    }

    @Override
    protected boolean isAir(BlockState state) {
        return true;
    }

    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        return false;
    }

    @Override
    public boolean canBeReplaced(BlockState pState, Fluid pFluid) {
        return true;
    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return true;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 15;
    }
}