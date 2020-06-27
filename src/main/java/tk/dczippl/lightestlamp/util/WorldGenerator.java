package tk.dczippl.lightestlamp.util;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import tk.dczippl.lightestlamp.Config;
import tk.dczippl.lightestlamp.init.ModBlocks;

import java.util.BitSet;
import java.util.Random;

public class WorldGenerator
{
    private static final CountRangeConfig MONAZITE_BLOCK = new CountRangeConfig(129, 43, 0, 128);
    private static final int MONAZITE_BLOCK_VEINSIZE = 3;

    public static void setupWorldGeneraton()
    {

        for (Biome biome : ForgeRegistries.BIOMES)
        {
            if (biome.getCategory() == Biome.Category.NETHER && biome != Biomes.field_235251_aB_)
            {
                if (Config.MONAZITE_SPAWN.get())
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,OreFeature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, ModBlocks.MONAZITE_ORE.get().getDefaultState(),MONAZITE_BLOCK_VEINSIZE))
                            .withPlacement( Placement.COUNT_RANGE.configure(MONAZITE_BLOCK)));
            }
        }
    }
}