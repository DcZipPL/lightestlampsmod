package dev.prefex.lightestlamp.datagen;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.datagen.tags.ModTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Util.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event){
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		generator.addProvider(true, new ModRecipeProvider(generator));
		generator.addProvider(true, new ModLootTableProvider(generator));
		generator.addProvider(true, new ModBlocksStateProvider(generator,existingFileHelper));
		generator.addProvider(true, new ModItemsModelProvider(generator,existingFileHelper));

		BlockTagsProvider btp = new ModTagsProvider.ModBlockTagsProvider(generator,existingFileHelper);
		generator.addProvider(btp);
		generator.addProvider(new ModTagsProvider.ModItemTagsProvider(generator,btp,existingFileHelper));
	}
}
