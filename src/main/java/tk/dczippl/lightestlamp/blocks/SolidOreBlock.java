package tk.dczippl.lightestlamp.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SolidOreBlock extends Block
{
    private int harvestlevel;

    public SolidOreBlock(Block.Properties properties,int harvestlevel)
    {
        super(properties);
        this.harvestlevel = harvestlevel;
    }

    @Override
    public int getHarvestLevel(BlockState state)
    {
        return harvestlevel;
    }
}
