package tk.dczippl.lightestlamp.potion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import tk.dczippl.lightestlamp.init.ModDamageSources;

import javax.annotation.Nullable;

public class BrominePoison extends Effect
{
    private int liquidColor;
    public BrominePoison(EffectType typeIn, int liquidColorIn)
    {
        super(typeIn, liquidColorIn);
        liquidColor = liquidColorIn;
    }

    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier)
    {
        if (!entityLivingBaseIn.world.isRemote)
            if (amplifier >= 20)
                entityLivingBaseIn.attackEntityFrom(ModDamageSources.BROMINE, 2.0F);
            else
            {
                entityLivingBaseIn.attackEntityFrom(ModDamageSources.BROMINE, 0.5F * (amplifier+1));
            }
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health)
    {
        this.performEffect(entityLivingBaseIn, amplifier);
        super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

    @Override
    public int getLiquidColor()
    {
        return liquidColor;
    }
}