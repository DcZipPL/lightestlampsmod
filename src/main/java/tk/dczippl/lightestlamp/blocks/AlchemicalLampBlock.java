package tk.dczippl.lightestlamp.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import tk.dczippl.lightestlamp.tile.AlchemicalLampBlockEntity;
import tk.dczippl.lightestlamp.tile.AntiLampBlockEntity;

import javax.annotation.Nullable;
import java.util.List;

public class AlchemicalLampBlock extends Block
{
    public AlchemicalLampBlock()
    {
        super(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.3f,1));
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }

    @Override
    public int getHarvestLevel(BlockState p_getHarvestLevel_1_)
    {
        return 0;
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
        return new AlchemicalLampBlockEntity();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> text, ITooltipFlag flag)
    {
        text.add(new TranslationTextComponent("tooltip.lightestlamp.type.alchemical").setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.GRAY))));
        text.add(new TranslationTextComponent("tooltip.lightestlamp.always_active").setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.GRAY))));
    }
}
