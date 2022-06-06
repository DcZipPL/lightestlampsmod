package tk.dczippl.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

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
