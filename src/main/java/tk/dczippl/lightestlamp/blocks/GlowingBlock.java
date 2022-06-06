package tk.dczippl.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class GlowingBlock extends Block {
	private int lightValue;
	public GlowingBlock(AbstractBlock.Properties properties, int lightValue) {
		super(properties);
		this.lightValue = lightValue;
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return lightValue;
	}
}
