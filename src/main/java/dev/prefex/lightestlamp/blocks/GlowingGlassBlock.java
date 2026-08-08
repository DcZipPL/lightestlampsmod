package dev.prefex.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;

public class GlowingGlassBlock extends TransparentBlock
{
    public GlowingGlassBlock()
    {
        super(Block.Properties.ofFullCopy(Blocks.GLASS));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 15;
    }
}
