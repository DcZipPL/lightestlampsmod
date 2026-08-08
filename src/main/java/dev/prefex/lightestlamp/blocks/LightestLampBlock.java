package dev.prefex.lightestlamp.blocks;

import com.mojang.serialization.MapCodec;
import dev.prefex.lightestlamp.entities.NormalLampBlockEntity;
import dev.prefex.lightestlamp.lighting.LampLightManager;
import dev.prefex.lightestlamp.lighting.LampProfile;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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

import java.util.List;

public class LightestLampBlock extends BaseEntityBlock {
    private final Type type;
    private final Tier tier;
    private final boolean luminant;
    private final LampProfile profile;

    public LightestLampBlock(Type type, Tier tier) {
        this(type, tier, false);
    }

    public LightestLampBlock(Type type, Tier tier, boolean luminant) {
        super(Properties.ofFullCopy(Blocks.GLOWSTONE));
        registerDefaultState(stateDefinition.any().setValue(BlockStateProperties.POWERED, false));
        this.type = type;
        this.tier = tier;
        this.luminant = luminant;
        this.profile = new LampProfile(
                tier.extension,
                type == Type.UNDERWATER ? LampProfile.Medium.UNDERWATER : LampProfile.Medium.AIR,
                luminant,
                type == Type.UNDERWATER || tier.alwaysActive
        );
    }

    public Type getType() {
        return type;
    }

    public Tier getTier() {
        return tier;
    }

    public boolean isLuminant() {
        return luminant;
    }

    public LampProfile profile() {
        return profile;
    }

    public boolean isActive(BlockState state) {
        return profile.alwaysActive() || !state.getValue(BlockStateProperties.POWERED);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return MapCodec.unit(this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.POWERED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NormalLampBlockEntity(pos, state);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return isActive(state) ? 15 : 0;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        if (oldState.getBlock() != this) {
            updateRedstoneState(serverLevel, pos, state);
            LampLightManager.register(serverLevel, pos, serverLevel.getBlockState(pos));
        } else {
            LampLightManager.schedule(serverLevel, pos, 0);
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock() && level instanceof ServerLevel serverLevel) {
            LampLightManager.unregister(serverLevel, pos);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        if (level instanceof ServerLevel serverLevel) {
            updateRedstoneState(serverLevel, pos, state);
        }
    }

    private void updateRedstoneState(ServerLevel level, BlockPos pos, BlockState state) {
        if (profile.alwaysActive()) {
            if (state.getValue(BlockStateProperties.POWERED)) {
                level.setBlock(pos, state.setValue(BlockStateProperties.POWERED, false), Block.UPDATE_CLIENTS);
            }
            LampLightManager.schedule(level, pos, 0);
            return;
        }
        boolean powered = level.hasNeighborSignal(pos);
        if (state.getValue(BlockStateProperties.POWERED) != powered) {
            level.setBlock(pos, state.setValue(BlockStateProperties.POWERED, powered), Block.UPDATE_CLIENTS);
        }
        LampLightManager.schedule(level, pos, 0);
    }

    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        return entity instanceof Player;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.lightestlamp.type." + tier.name().toLowerCase()).withStyle(ChatFormatting.GRAY));
        if (type == Type.UNDERWATER) {
            tooltip.add(Component.translatable("tooltip.lightestlamp.underwater").withStyle(ChatFormatting.GRAY));
        }
        tooltip.add(Component.translatable(profile.alwaysActive()
                ? "tooltip.lightestlamp.always_active"
                : "tooltip.lightestlamp.inverted").withStyle(ChatFormatting.GRAY));
        if (luminant) {
            tooltip.add(Component.translatable("tooltip.lightestlamp.penetration").withStyle(ChatFormatting.AQUA));
        }
        super.appendHoverText(stack, context, tooltip, flag);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return null;
    }

    public enum Tier {
        ALPHA(1),
        BETA(2),
        GAMMA(3),
        DELTA(4),
        EPSILON(5),
        ZETA(9, true),
        ETA(11, true),
        OMEGA(15, true);

        public final int extension;
        public final boolean alwaysActive;

        Tier(int extension) {
            this(extension, false);
        }

        Tier(int extension, boolean alwaysActive) {
            this.extension = extension;
            this.alwaysActive = alwaysActive;
        }
    }

    public enum Type {
        NORMAL,
        UNDERWATER
    }
}
