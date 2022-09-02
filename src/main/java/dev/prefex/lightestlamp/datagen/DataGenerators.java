package dev.prefex.lightestlamp.datagen;

import dev.prefex.lightestlamp.Main;
import dev.prefex.lightestlamp.Reference;
import dev.prefex.lightestlamp.datagen.tags.ModTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event){
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		generator.addProvider(new ModRecipeProvider(generator));
		generator.addProvider(new ModLootTableProvider(generator));
		generator.addProvider(new ModBlocksStateProvider(generator,existingFileHelper));
		generator.addProvider(new ModItemsModelProvider(generator,existingFileHelper));

		BlockTagsProvider btp = new ModTagsProvider.ModBlockTagsProvider(generator,existingFileHelper);
		generator.addProvider(btp);
		generator.addProvider(new ModTagsProvider.ModItemTagsProvider(generator,btp,existingFileHelper));
	}
}
