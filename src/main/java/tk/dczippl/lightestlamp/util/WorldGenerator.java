package tk.dczippl.lightestlamp.util;

import net.minecraft.world.gen.feature.*;

import java.util.ArrayList;

public class WorldGenerator
{
    public static final ArrayList<ConfiguredFeature<?, ?>> netherOres = new ArrayList<ConfiguredFeature<?, ?>>();

    public static final FeatureSpreadConfig MONAZITE_BLOCK = new FeatureSpreadConfig(FeatureSpread.func_242253_a(129, 43));
    public static final int MONAZITE_BLOCK_VEINSIZE = 3;
}