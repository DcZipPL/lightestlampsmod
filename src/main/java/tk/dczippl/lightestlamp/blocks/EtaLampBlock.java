package tk.dczippl.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import tk.dczippl.lightestlamp.init.ModBlocks;

import javax.annotation.Nullable;

public class EtaLampBlock extends Block
{
    public EtaLampBlock()
    {
        super(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.3f,1));
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
        return new EtaLampBlockEntity();
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
        world.setBlockState(pos, ModBlocks.CHUNK_CLEANER.get().getDefaultState());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> text, ITooltipFlag flag)
    {
        text.add(new TranslationTextComponent("tooltip.lightestlamp.type.eta").setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.GRAY))));
        text.add(new TranslationTextComponent("tooltip.lightestlamp.penetration").setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.GRAY))));
        text.add(new TranslationTextComponent("tooltip.lightestlamp.always_active").setStyle(Style.EMPTY.setColor(Color.fromTextFormatting(TextFormatting.GRAY))));
    }
}