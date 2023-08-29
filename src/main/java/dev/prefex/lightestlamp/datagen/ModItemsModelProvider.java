package dev.prefex.lightestlamp.datagen;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.init.ModBlocks;
import dev.prefex.lightestlamp.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemsModelProvider extends ItemModelProvider {
	public ModItemsModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator.getPackOutput(), Util.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		ModItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(item -> {
			withExistingParent(ForgeRegistries.ITEMS.getKey(item).getPath(),new ResourceLocation("item/generated"))
					.texture("layer0",new ResourceLocation(Util.MOD_ID,"item/"+ForgeRegistries.ITEMS.getKey(item).getPath()));
		});
		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			if (block == ModBlocks.CURTAIN_BLOCK.get()){
				withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath(),new ResourceLocation("item/generated"))
						.texture("layer0",new ResourceLocation(Util.MOD_ID,"block/"+ForgeRegistries.BLOCKS.getKey(block).getPath()));
			}
			else if (block != ModBlocks.OCC.get()
					&& block != ModBlocks.LIGHT_AIR.get()
					&& block != ModBlocks.WATERLOGGABLE_LIGHT_AIR.get()
					&& block != ModBlocks.JUNGLE_LANTERN.get()) {
				withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath(), Util.MOD_ID+":block/"+ForgeRegistries.BLOCKS.getKey(block).getPath());
			}
		});
	}
}
