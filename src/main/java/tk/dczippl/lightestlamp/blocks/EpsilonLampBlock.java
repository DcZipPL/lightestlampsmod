package tk.dczippl.lightestlamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.tile.DeltaLampTileEntity;
import tk.dczippl.lightestlamp.tile.EpsilonLampTileEntity;

import javax.annotation.Nullable;
import java.util.List;

public class EpsilonLampBlock extends Block
{
    public EpsilonLampBlock()
    {
        super(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).lightValue(15).hardnessAndResistance(0.3f,1));
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
        return new EpsilonLampTileEntity();
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
        world.setBlockState(pos, ModBlocks.CHUNK_CLEANER.getDefaultState());
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        BlockPos.getAllInBox(pos.offset(Direction.UP, 4).offset(Direction.NORTH, 4).offset(Direction.WEST, 4),
                pos.offset(Direction.DOWN, 4).offset(Direction.SOUTH, 4).offset(Direction.EAST, 4)).forEach((pos2) ->
        {
            if (isWAir(pos2,world))
            {
                world.setBlockState(pos2, ModBlocks.LIGHT_AIR.getDefaultState());
            }
        });
        BlockPos.getAllInBox(pos.offset(Direction.UP, 12).offset(Direction.NORTH,12).offset(Direction.WEST,12), pos.offset(Direction.DOWN,12).offset(Direction.SOUTH,12).offset(Direction.EAST,12)).forEach((pos1) -> {
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
        text.add(new TranslationTextComponent("tooltip.lightestlamp.type.epsilon").applyTextStyle(TextFormatting.GRAY));
        text.add(new TranslationTextComponent("tooltip.lightestlamp.penetration").applyTextStyle(TextFormatting.GRAY));
        text.add(new TranslationTextComponent("tooltip.lightestlamp.inverted").applyTextStyle(TextFormatting.GRAY));
    }
}