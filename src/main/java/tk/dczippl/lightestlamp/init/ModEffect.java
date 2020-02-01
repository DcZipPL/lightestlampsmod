package tk.dczippl.lightestlamp.init;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.InstantEffect;
import net.minecraft.potion.Potion;
import tk.dczippl.lightestlamp.potion.BrominePoison;

import java.awt.*;

public class ModEffect
{
    public static final Effect BROMINE_POISON = new BrominePoison(EffectType.HARMFUL,new Color(102,16,0).getRGB()).setRegistryName("bromine_poison");
}