package dev.prefex.lightestlamp.datagen;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.datagen.tags.ModTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = Util.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		event.getGenerator().addProvider(
				// Tell generator to run only when server data are generating
				event.includeServer(),
				(DataProvider.Factory<ModRecipeProvider>) ModRecipeProvider::new
		);

		event.getGenerator().addProvider(
				// Tell generator to run only when server data are generating
				event.includeServer(),
				(DataProvider.Factory<ModLootTableProvider>) output -> new ModLootTableProvider(
						output,
						// Specify registry names of tables that are required to generate, or can leave empty
						Collections.emptySet(),
						// Sub providers which generate the loot
						List.of(new LootTableProvider.SubProviderEntry(ModLootTables::new, LootContextParamSets.BLOCK))
				)
		);


		DataGenerator gen = event.getGenerator();
		ExistingFileHelper efh = event.getExistingFileHelper();

		gen.addProvider(
				// Tell generator to run only when client assets are generating
				event.includeClient(),
				(DataProvider.Factory<ModItemsModelProvider>) output -> new ModItemsModelProvider(output, Util.MOD_ID, efh)
		);
		gen.addProvider(
				event.includeClient(),
				(DataProvider.Factory<ModBlocksStateProvider>) output -> new ModBlocksStateProvider(output, Util.MOD_ID, efh)
		);

		event.getGenerator().addProvider(
				// Tell generator to run only when server data are generating
				event.includeServer(),
				// Extends net.minecraftforge.common.data.BlockTagsProvider
				(DataProvider.Factory<ModTagsProvider.ModBlockTagsProvider>) output -> new ModTagsProvider.ModBlockTagsProvider(
						output,
						event.getLookupProvider(),
						Util.MOD_ID,
						event.getExistingFileHelper()
				)
		);
	}
}
