package tk.dczippl.lightestlamp.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tk.dczippl.lightestlamp.tile.NormalLampBlockEntity;

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

    public enum Tier{
        ALPHA(1),
        BETA(2),
        GAMMA(2),
        DELTA(4),
        EPSILON(5),
        ZETA(0),
        ETA(0),
        OMEGA(0);

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
