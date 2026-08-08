package dev.prefex.lightestlamp.datagen;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.init.ModBlocks;
import dev.prefex.lightestlamp.init.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItemsModelProvider extends ItemModelProvider {

	public ModItemsModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
		super(output, modid, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		ModItems.ITEMS.getEntries().stream().map(DeferredHolder::get).forEach(item -> {
			if (item instanceof BlockItem) {
				return;
			}
			String path = BuiltInRegistries.ITEM.getKey(item).getPath();
			withExistingParent(path, ResourceLocation.withDefaultNamespace("item/generated"))
					.texture("layer0", ResourceLocation.fromNamespaceAndPath(Util.MOD_ID, "item/" + path));
		});
		ModBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).forEach(block -> {
			if (block != ModBlocks.OCC.get()
					&& block != ModBlocks.LIGHT_AIR.get()
					&& block != ModBlocks.WATERLOGGABLE_LIGHT_AIR.get()
					&& block != ModBlocks.JUNGLE_LANTERN.get()
					&& block != ModBlocks.CURTAIN_BLOCK.get()) {
				String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
				if (path.startsWith("luminant_")) {
					String basePath = path.substring("luminant_".length());
					withExistingParent(path, ResourceLocation.fromNamespaceAndPath(Util.MOD_ID, "item/luminant_lamp_template"))
							.texture("base", ResourceLocation.fromNamespaceAndPath(Util.MOD_ID, "block/" + basePath))
							.texture("overlay", ResourceLocation.fromNamespaceAndPath(Util.MOD_ID, "item/luminant_overlay"));
				} else {
					withExistingParent(path, Util.MOD_ID + ":block/" + path);
				}
			}
		});
	}
}
