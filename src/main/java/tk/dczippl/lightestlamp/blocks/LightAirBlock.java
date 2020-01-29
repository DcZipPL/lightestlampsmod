package tk.dczippl.lightestlamp.blocks;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import tk.dczippl.lightestlamp.tile.LightAirTileEntity;

import javax.annotation.Nullable;

@SuppressWarnings({"NullableProblems", "deprecation"})
public class LightAirBlock extends AirBlock
{
    public LightAirBlock()
    {
        super(Block.Properties.create(Material.AIR).lightValue(15));
    }

    @Override
    public BlockRenderType getRenderType(BlockState p_149645_1_)
    {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader world, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean canBeConnectedTo(BlockState state, IBlockReader world, BlockPos pos, Direction facing)
    {
        return false;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side)
    {
        return false;
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
    public boolean isAir(BlockState state)
    {
        return true;
    }
}