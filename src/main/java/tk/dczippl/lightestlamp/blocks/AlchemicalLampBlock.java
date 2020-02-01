package tk.dczippl.lightestlamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import tk.dczippl.lightestlamp.tile.AlchemicalLampTileEntity;
import tk.dczippl.lightestlamp.tile.AntiLampTileEntity;

import javax.annotation.Nullable;
import java.util.List;

public class AlchemicalLampBlock extends Block
{
    public AlchemicalLampBlock()
    {
        super(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).lightValue(15).hardnessAndResistance(0.3f,1));
    }

    @Override
    public int getHarvestLevel(BlockState p_getHarvestLevel_1_)
    {
        return 0;
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
        return new AlchemicalLampTileEntity();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> text, ITooltipFlag flag)
    {
        text.add(new TranslationTextComponent("tooltip.lightestlamp.type.alchemical").applyTextStyle(TextFormatting.GRAY));
    }
}
