package tk.dczippl.lightestlamp.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tk.dczippl.lightestlamp.entities.AlchemicalLampBlockEntity;

import java.util.List;

public class AlchemicalLampBlock extends BaseEntityBlock
{
    public AlchemicalLampBlock()
    {
        super(Block.Properties.copy(Blocks.REDSTONE_LAMP));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 15;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AlchemicalLampBlockEntity(pPos,pState);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @org.jetbrains.annotations.Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(new TranslatableComponent("tooltip.lightestlamp.type.alchemical").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        pTooltip.add(new TranslatableComponent("tooltip.lightestlamp.inverted").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
    }
}
