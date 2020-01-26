package tk.dczippl.lightestlamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

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
