package tk.dczippl.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class GlowingRotatedPillarBlock extends RotatedPillarBlock {
	private final int lightValue;
	public GlowingRotatedPillarBlock(BlockBehaviour.Properties properties, int lightValue) {
		super(properties);
		this.lightValue = lightValue;
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
		return lightValue;
	}
}
