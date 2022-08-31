package dev.prefex.lightestlamp.datagen;

import dev.prefex.lightestlamp.Reference;
import dev.prefex.lightestlamp.init.ModBlocks;
import dev.prefex.lightestlamp.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemsModelProvider extends ItemModelProvider {
	public ModItemsModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Reference.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		ModItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(item -> {
			withExistingParent(item.getRegistryName().getPath(),new ResourceLocation("item/generated"))
					.texture("layer0",new ResourceLocation(Reference.MOD_ID,"item/"+item.getRegistryName().getPath()));
		});
		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			if (block == ModBlocks.CURTAIN_BLOCK.get()){
				withExistingParent(block.getRegistryName().getPath(),new ResourceLocation("item/generated"))
						.texture("layer0",new ResourceLocation(Reference.MOD_ID,"block/"+block.getRegistryName().getPath()));
			}
			else if (block != ModBlocks.OCC.get()
					&& block != ModBlocks.LIGHT_AIR.get()
					&& block != ModBlocks.WATERLOGGABLE_LIGHT_AIR.get()
					&& block != ModBlocks.JUNGLE_LANTERN.get()) {
				withExistingParent(block.getRegistryName().getPath(), Reference.MOD_ID+":block/"+block.getRegistryName().getPath());
			}
		});
	}
}
