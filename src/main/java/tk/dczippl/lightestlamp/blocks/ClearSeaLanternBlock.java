package tk.dczippl.lightestlamp.blocks;

import tk.dczippl.lightestlamp.init.ModBlocks;

import javax.annotation.Nullable;

public class ClearSeaLanternBlock extends Block {
    public ClearSeaLanternBlock() {
        super(Block.Properties.create(Material.REDSTONE_LIGHT).sound(SoundType.GLASS)
                .hardnessAndResistance(0.3f,1));
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }

    @Override
    public boolean hasBlockEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockState state, IBlockReader world)
    {
        return new ClearSeaLanternBlockEntity();
    }

    @Override
    public int getHarvestLevel(BlockState p_getHarvestLevel_1_)
    {
        return 0;
    }

    @Override
    public void onPlayerDestroy(IWorld iworld, BlockPos pos, BlockState state)
    {
        World world = (World) iworld;
        BlockPos.getAllInBox(pos.offset(Direction.UP, 2).offset(Direction.NORTH,2).offset(Direction.WEST,2), pos.offset(Direction.DOWN, 2).offset(Direction.SOUTH,2).offset(Direction.EAST,2)).forEach((pos1) -> {
            if (world.getBlockState(pos1).getBlock() == ModBlocks.WATERLOGGABLE_LIGHT_AIR.get())
            {
                if (world.getBlockState(pos1).get(WATERLOGGED))
                    world.setBlockState(pos1, Blocks.WATER.getDefaultState());
                else
                    world.setBlockState(pos1, Blocks.AIR.getDefaultState());
            }
        });
    }
}