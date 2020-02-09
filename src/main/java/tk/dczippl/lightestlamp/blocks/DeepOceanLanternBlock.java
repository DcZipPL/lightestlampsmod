package tk.dczippl.lightestlamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.tile.DeepOceanLanternTileEntity;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class DeepOceanLanternBlock extends Block
{
    public DeepOceanLanternBlock() {
        super(Block.Properties.create(Material.REDSTONE_LIGHT).sound(SoundType.GLASS)
                .hardnessAndResistance(0.3f,1).lightValue(15));
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new DeepOceanLanternTileEntity();
    }

    @Override
    public int getHarvestLevel(BlockState p_getHarvestLevel_1_)
    {
        return 0;
    }

    @Override
    public void onPlayerDestroy(IWorld iworld, BlockPos pos, BlockState state)
    {
        World world = iworld.getWorld();
        BlockPos.getAllInBox(pos.offset(Direction.UP, 4).offset(Direction.NORTH,4).offset(Direction.WEST,4), pos.offset(Direction.DOWN, 4).offset(Direction.SOUTH,4).offset(Direction.EAST,4)).forEach((pos1) -> {
            if (world.getBlockState(pos1).getBlock() == ModBlocks.WATERLOGGABLE_LIGHT_AIR)
            {
                if (world.getBlockState(pos1).get(WATERLOGGED))
                    world.setBlockState(pos1, Blocks.WATER.getDefaultState());
                else
                    world.setBlockState(pos1, Blocks.AIR.getDefaultState());
            }
        });
        BlockPos.getAllInBox(pos.offset(Direction.UP, 8).offset(Direction.NORTH,8).offset(Direction.WEST,8), pos.offset(Direction.DOWN,8).offset(Direction.SOUTH,8).offset(Direction.EAST,8)).forEach((pos1) -> {
            if (isWAir(pos1,world))
            {
                world.notifyBlockUpdate(pos1, world.getBlockState(pos1), world.getBlockState(pos1), 3);
            }
        });
    }

    private boolean isWAir(BlockPos pos, World world)
    {
        return world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos).getBlock() == Blocks.CAVE_AIR || world.getBlockState(pos).getBlock() == ModBlocks.LIGHT_AIR;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> text, ITooltipFlag flag)
    {
        text.add(new TranslationTextComponent("tooltip.lightestlamp.type.delta").applyTextStyle(TextFormatting.GRAY));
    }
}