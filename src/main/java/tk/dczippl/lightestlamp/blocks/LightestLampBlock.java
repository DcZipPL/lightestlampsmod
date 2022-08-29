package tk.dczippl.lightestlamp.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tk.dczippl.lightestlamp.entities.NormalLampBlockEntity;

import java.util.List;

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
        super(Properties.copy(Blocks.REDSTONE_LAMP));
        _type = type;
        _tier = tier;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new NormalLampBlockEntity(pPos,pState);
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        super.destroy(pLevel, pPos, pState);
    }

    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
        return entity instanceof Player;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(new TranslatableComponent("tooltip.lightestlamp.type."+_tier.name().toLowerCase()).setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        if (_tier.ordinal() > 4)
            pTooltip.add(new TranslatableComponent("tooltip.lightestlamp.always_active").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        else
            pTooltip.add(new TranslatableComponent("tooltip.lightestlamp.inverted").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        if (_tier.ordinal() > 1)
            pTooltip.add(new TranslatableComponent("tooltip.lightestlamp.penetration").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));

        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    public enum Tier{
        ALPHA(1),
        BETA(2),
        GAMMA(2),
        DELTA(4),
        EPSILON(5),
        ZETA(0),
        ETA(0);

        public final int power;
        Tier(int power) {
            this.power = power;
        }
    }

    public enum Type{
        NORMAL,
        UNDERWATER
    }
}
