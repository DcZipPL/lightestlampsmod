package dev.prefex.lightestlamp.datagen;

import dev.prefex.lightestlamp.init.ModBlocks;
import dev.prefex.lightestlamp.init.ModItems;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

public class ModLootTables extends BlockLoot {
	@Override
	protected void addTables() {
		this.dropSelf(ModBlocks.CLEAR_LAMP.get());
		this.dropSelf(ModBlocks.ALPHA_LAMP.get());
		this.dropSelf(ModBlocks.BETA_LAMP.get());
		this.dropSelf(ModBlocks.GAMMA_LAMP.get());
		this.dropSelf(ModBlocks.DELTA_LAMP.get());
		this.dropSelf(ModBlocks.EPSILON_LAMP.get());
		this.dropSelf(ModBlocks.ZETA_LAMP.get());
		this.dropSelf(ModBlocks.ETA_LAMP.get());
		this.dropSelf(ModBlocks.OMEGA_LAMP.get());
		this.dropSelf(ModBlocks.CLEAR_SEA_LANTERN.get());
		this.dropSelf(ModBlocks.DEEP_SEA_LANTERN.get());
		this.dropSelf(ModBlocks.OCEAN_LANTERN.get());
		this.dropSelf(ModBlocks.DEEP_OCEAN_LANTERN.get());
		this.dropSelf(ModBlocks.ABYSSAL_LANTERN.get());
		this.dropSelf(ModBlocks.ALCHEMICAL_LAMP.get());
		this.dropSelf(ModBlocks.JUNGLE_LANTERN.get());
		this.dropSelf(ModBlocks.NEON_TUBE_BLOCK.get());
		this.dropSelf(ModBlocks.ARGON_TUBE_BLOCK.get());
		this.dropSelf(ModBlocks.KRYPTON_TUBE_BLOCK.get());
		this.dropSelf(ModBlocks.XENON_TUBE_BLOCK.get());
		this.dropSelf(ModBlocks.RADON_TUBE_BLOCK.get());
		this.dropSelf(ModBlocks.VANTA_BLACK.get());
		this.dropSelf(ModBlocks.CURTAIN_BLOCK.get());
		this.add(ModBlocks.NEON_BLOCK.get(), block ->createSingleItemTableWithSilkTouch(
				block, ModItems.NEON_DUST.get(), UniformGenerator.between(1,4)
		));
		this.add(ModBlocks.ARGON_BLOCK.get(), block ->createSingleItemTableWithSilkTouch(
				block, ModItems.ARGON_DUST.get(), UniformGenerator.between(1,4)
		));
		this.add(ModBlocks.KRYPTON_BLOCK.get(), block ->createSingleItemTableWithSilkTouch(
				block, ModItems.KRYPTON_DUST.get(), UniformGenerator.between(1,4)
		));
		this.add(ModBlocks.XENON_BLOCK.get(), block ->createSingleItemTableWithSilkTouch(
				block, ModItems.XENON_DUST.get(), UniformGenerator.between(1,4)
		));
		this.add(ModBlocks.RADON_BLOCK.get(), block ->createSingleItemTableWithSilkTouch(
				block, ModItems.RADON_DUST.get(), UniformGenerator.between(1,4)
		));
		this.add(ModBlocks.GLOWING_GLASS_BLOCK.get(), block ->createSingleItemTableWithSilkTouch(
				block, Items.GLOWSTONE_DUST, UniformGenerator.between(1,4)
		));
		this.add(ModBlocks.LANTHANUM_ORE.get(), block -> createOreDrop(ModBlocks.LANTHANUM_ORE.get(),ModItems.RAW_LANTHANUM.get()));
		this.dropSelf(ModBlocks.GAS_EXTRACTOR.get());
		this.dropSelf(ModBlocks.CURTAIN_BLOCK.get());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		// Exclusion list
		return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(block ->
				block != ModBlocks.LIGHT_AIR.get()
				&& block != ModBlocks.WATERLOGGABLE_LIGHT_AIR.get()
				&& block != ModBlocks.OCC.get()
		)::iterator;
	}
}
