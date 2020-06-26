package tk.dczippl.lightestlamp.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class GlowingRotatedPillarBlock extends RotatedPillarBlock {
	private int lightValue;
	public GlowingRotatedPillarBlock(AbstractBlock.Properties properties, int lightValue) {
		super(properties);
		this.lightValue = lightValue;
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return lightValue;
	}
}
