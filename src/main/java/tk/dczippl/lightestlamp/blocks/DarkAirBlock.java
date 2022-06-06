package tk.dczippl.lightestlamp.blocks;

public class DarkAirBlock extends Block
{
    VoxelShape vs = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

    public DarkAirBlock() {
        super(Block.Properties.create(Material.IRON));
    }

    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return vs;
    }
}
