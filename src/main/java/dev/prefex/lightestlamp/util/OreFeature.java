package dev.prefex.lightestlamp.util;

import dev.prefex.lightestlamp.init.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreFeature {
	public static Holder<PlacedFeature> NETHER_OREGEN;
	public static void registerOreFeatures() {
		OreConfiguration overworldConfig = new OreConfiguration(OreFeatures.NETHER_ORE_REPLACEABLES, ModBlocks.MONAZITE_ORE.get().defaultBlockState(), 4);
		NETHER_OREGEN = registerPlacedOreFeature("nether_lanthanum_ore", new ConfiguredFeature<>(Feature.ORE, overworldConfig),
				CountPlacement.of(22),
				InSquarePlacement.spread(),
				BiomeFilter.biome(),
				HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(128)));
	}

	private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<PlacedFeature> registerPlacedOreFeature(String registryName, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
		return PlacementUtils.register(registryName, Holder.direct(feature), placementModifiers);
	}

	public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
		if (event.getCategory() == Biome.BiomeCategory.NETHER) {
			event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, NETHER_OREGEN);
		}
	}
}