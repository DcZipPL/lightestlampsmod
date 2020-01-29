package tk.dczippl.lightestlamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
import tk.dczippl.lightestlamp.tile.OmegaLampTileEntity;

import javax.annotation.Nullable;
import java.util.List;

public class OmegaLampBlock extends Block
{
    public OmegaLampBlock()
    {
        super(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).lightValue(15).hardnessAndResistance(1f,1));
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
        return new OmegaLampTileEntity();
    }

    @Override
    public int getHarvestLevel(BlockState p_getHarvestLevel_1_)
    {
        return 0;
    }

    @Override
    public void onPlayerDestroy(IWorld iworld, BlockPos pos, BlockState state)
    {
        RemoveLightBlocks(iworld, pos);
    }

    private void RemoveLightBlocks(IWorld iworld, BlockPos pos)
    {
        World world = (World) iworld;
        world.setBlockState(pos, ModBlocks.CHUNK_CLEANER.getDefaultState());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> text, ITooltipFlag flag)
    {
        text.add(new TranslationTextComponent("tooltip.lightestlamp.type.omega").applyTextStyle(TextFormatting.GRAY));
        text.add(new TranslationTextComponent("tooltip.lightestlamp.penetration").applyTextStyle(TextFormatting.GRAY));
        text.add(new TranslationTextComponent("tooltip.lightestlamp.inverted").applyTextStyle(TextFormatting.GRAY));
    }
}