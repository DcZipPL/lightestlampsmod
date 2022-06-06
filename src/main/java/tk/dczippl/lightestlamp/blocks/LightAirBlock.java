package tk.dczippl.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;

@SuppressWarnings({"NullableProblems", "deprecation"})
public class LightAirBlock extends AirBlock
{
    public LightAirBlock()
    {
        super(Block.Properties.create(Material.AIR).notSolid().setAir().setLightLevel(x->15));
    }

    @Override
    public BlockRenderType getRenderType(BlockState p_149645_1_)
    {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public boolean canStickTo(BlockState state, BlockState other) {
        return false;
    }

    @Override
    public boolean canSpawnInBlock() {
        return true;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side)
    {
        return false;
    }

    @Override
    public boolean isReplaceable(BlockState p_225541_1_, Fluid p_225541_2_) {
        return true;
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return true;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return isValid(worldIn,pos);
    }

    private boolean isValid(IWorldReader world, BlockPos pos){
        return ((World) world.getChunk(pos).getWorldForge()).getBlockState(pos.up()).getBlock() != Blocks.VINE;
    }


    @Override
    public boolean canBeReplacedByLeaves(BlockState state, IWorldReader world, BlockPos pos)
    {
        return true;
    }

    @Override
    public boolean canBeReplacedByLogs(BlockState state, IWorldReader world, BlockPos pos)
    {
        return true;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }

    @Override
    public boolean isAir(BlockState state, IBlockReader world, BlockPos pos) {
        return true;
    }
}