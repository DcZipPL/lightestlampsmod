package dev.prefex.lightestlamp.datagen;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocksStateProvider extends BlockStateProvider {
	public ModBlocksStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen.getPackOutput(), Util.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
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
				simpleBlock(ModBlocks.GLOWSTONE_CENTRIFUGE.get(), models().cubeBottomTop(ForgeRegistries.BLOCKS.getKey(block).getPath(),
						new ResourceLocation(Util.MOD_ID,"block/machine/"+ ForgeRegistries.BLOCKS.getKey(block).getPath()+"_side"),
						new ResourceLocation(Util.MOD_ID,"block/machine/"+ForgeRegistries.BLOCKS.getKey(block).getPath()+"_bottom"),
						new ResourceLocation(Util.MOD_ID,"block/machine/"+ForgeRegistries.BLOCKS.getKey(block).getPath())));
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
