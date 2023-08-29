package dev.prefex.lightestlamp.blocks;

import dev.prefex.lightestlamp.init.ModBlockEntities;
import dev.prefex.lightestlamp.init.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import dev.prefex.lightestlamp.entities.NormalLampBlockEntity;

import java.util.List;

import static dev.prefex.lightestlamp.entities.NormalLampBlockEntity.*;

public class LightestLampBlock extends BaseEntityBlock {
    private final Type _type;
    private final Tier _tier;

    public Type getType() {
        return _type;
    }

    public Tier getTier() {
        return _tier;
    }

    public LightestLampBlock(Type type, Tier tier){
        super(Properties.copy(Blocks.GLOWSTONE));
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.POWERED, Boolean.FALSE));
        _type = type;
        _tier = tier;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(BlockStateProperties.POWERED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new NormalLampBlockEntity(pPos,pState);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(BlockStateProperties.POWERED) ? 0 : 15;
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
        Block lightBlockType = getType() == LightestLampBlock.Type.NORMAL ? ModBlocks.LIGHT_AIR.get() : ModBlocks.WATERLOGGABLE_LIGHT_AIR.get();
        if (_tier == LightestLampBlock.Tier.ALPHA) tickAlphaLamp(pLevel, pPos, pState, _type, lightBlockType);
        else if (_tier == LightestLampBlock.Tier.BETA) {tickBetaLamp(pLevel, pPos, pState, _type, lightBlockType); tickLamp(pLevel, pPos, pState, lightBlockType, _type, _tier.power);}
        else if (_tier.ordinal() > 1) tickLamp(pLevel, pPos, pState, lightBlockType, _type, _tier.power);
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        super.destroy(pLevel, pPos, pState);
        NormalLampBlockEntity.cleanLight((Level) pLevel,pPos,pState, _tier.power, true);
    }

    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        return entity instanceof Player;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.lightestlamp.type."+_tier.name().toLowerCase()).setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        if (_tier.ordinal() > 4)
            pTooltip.add(Component.translatable("tooltip.lightestlamp.always_active").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        else
            pTooltip.add(Component.translatable("tooltip.lightestlamp.inverted").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        if (_tier.ordinal() > 1)
            pTooltip.add(Component.translatable("tooltip.lightestlamp.penetration").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
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
        return pBlockEntityType == ModBlockEntities.NORMAL_LAMP_BE.get() ? NormalLampBlockEntity::tick : null;
    }

    public enum Tier{
        ALPHA(1),
        BETA(2),
        GAMMA(2),
        DELTA(4),
        EPSILON(5),
        ZETA(9, true),
        ETA(11, true);

        public final int power;
        public final boolean always_active;
        Tier(int power) {
            this(power, false);
        }
        Tier(int power, boolean always_active) {
            this.power = power;
            this.always_active = always_active;
        }
    }

    public enum Type{
        NORMAL,
        UNDERWATER
    }
}
