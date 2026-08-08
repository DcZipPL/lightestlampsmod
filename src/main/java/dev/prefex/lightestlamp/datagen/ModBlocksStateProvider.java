package dev.prefex.lightestlamp.datagen;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.init.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModBlocksStateProvider extends BlockStateProvider {

	public ModBlocksStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
		super(output, modid, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		ModBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).forEach(block -> {
			if (block instanceof RotatedPillarBlock){
				axisBlock((RotatedPillarBlock) block);
			}
			else if (block == ModBlocks.CLEAR_LAMP.get()
					|| block == ModBlocks.ALPHA_LAMP.get()
					|| block == ModBlocks.BETA_LAMP.get()
					|| block == ModBlocks.GAMMA_LAMP.get()
					|| block == ModBlocks.DELTA_LAMP.get()
					|| block == ModBlocks.EPSILON_LAMP.get()){
				// TODO: add lamp models here
			}
			else if (block == ModBlocks.GLOWSTONE_CENTRIFUGE.get()){
				String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
				simpleBlock(ModBlocks.GLOWSTONE_CENTRIFUGE.get(), models().cubeBottomTop(path,
						ResourceLocation.fromNamespaceAndPath(Util.MOD_ID, "block/machine/" + path + "_side"),
						ResourceLocation.fromNamespaceAndPath(Util.MOD_ID, "block/machine/" + path + "_bottom"),
						ResourceLocation.fromNamespaceAndPath(Util.MOD_ID, "block/machine/" + path)));
			}
			else if (block == ModBlocks.GLOWING_GLASS_BLOCK.get()) {
				simpleBlock(block, models().cubeAll(BuiltInRegistries.BLOCK.getKey(block).getPath(), blockTexture(block)).renderType("minecraft:cutout"));
			}
			else if (block != ModBlocks.CURTAIN_BLOCK.get()
					&& block != ModBlocks.OCC.get()
					&& block != ModBlocks.JUNGLE_LANTERN.get()
					&& block != ModBlocks.LIGHT_AIR.get()
					&& block != ModBlocks.WATERLOGGABLE_LIGHT_AIR.get()){
				simpleBlock(block);
			}
		});
	}
}
