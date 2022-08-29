package dev.prefex.lightestlamp.blocks;

import dev.prefex.lightestlamp.entities.OmegaLampBlockEntity;
import dev.prefex.lightestlamp.init.ModBlockEntities;
import dev.prefex.lightestlamp.init.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OmegaLampBlock extends BaseEntityBlock
{
    public OmegaLampBlock()
    {
        super(Block.Properties.copy(Blocks.GLOWSTONE));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 15;
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        super.destroy(pLevel, pPos, pState);

        ((Level)pLevel).setBlockAndUpdate(pPos, ModBlocks.OCC.get().defaultBlockState());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @org.jetbrains.annotations.Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(new TranslatableComponent("tooltip.lightestlamp.type.omega").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        pTooltip.add(new TranslatableComponent("tooltip.lightestlamp.penetration").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        pTooltip.add(new TranslatableComponent("tooltip.lightestlamp.always_active").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new OmegaLampBlockEntity(pPos, pState);
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     * Implementing/overriding is fine.
     */
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pBlockEntityType == ModBlockEntities.OMEGA_BE.get() ? OmegaLampBlockEntity::tick : null;
    }
}