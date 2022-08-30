package dev.prefex.lightestlamp.blocks;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CurtainBlock extends IronBarsBlock {
	public CurtainBlock() {
		super(BlockBehaviour.Properties.copy(Blocks.BLACK_WOOL).noCollission());
	}
}
