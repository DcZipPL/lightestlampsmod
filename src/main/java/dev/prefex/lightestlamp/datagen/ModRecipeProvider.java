package dev.prefex.lightestlamp.datagen;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.init.ModBlocks;
import dev.prefex.lightestlamp.init.ModItems;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeRecipe;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(PackOutput pOutput) {
		super(pOutput);
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
		TagKey<Item> lanthanum_ingot = ItemTags.create(new ResourceLocation("forge","ingots/lanthanum"));
		TagKey<Item> lanthanum_nugget = ItemTags.create(new ResourceLocation("forge","nuggets/lanthanum"));

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ABYSSAL_LANTERN.get())
				.define('L', ModBlocks.DEEP_OCEAN_LANTERN.get())
				.define('H', Items.HEART_OF_THE_SEA)
				.define('R', ModBlocks.XENON_TUBE_BLOCK.get())
				.pattern("RHR")
				.pattern("HLH")
				.pattern("RHR")
				.unlockedBy("has_deep_ocean_lantern", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModBlocks.DEEP_OCEAN_LANTERN.get())
						.build()))
				.save(pWriter);
		// Skipped: ModItems.ALCHEMICAL_DUST.get()
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALCHEMICAL_LAMP.get())
				.define('D', ModItems.ALCHEMICAL_DUST.get())
				.define('L', ModBlocks.CLEAR_LAMP.get())
				.pattern("DDD")
				.pattern("DLD")
				.pattern("DDD")
				.unlockedBy("has_alchemical_dust", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModItems.ALCHEMICAL_DUST.get())
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALPHA_LAMP.get())
				.define('L', ModBlocks.CLEAR_LAMP.get())
				.define('T', ModItems.NEON_TUBE.get())
				.pattern(" T ")
				.pattern("TLT")
				.pattern(" T ")
				.unlockedBy("has_clear_lamp", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModBlocks.CLEAR_LAMP.get())
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BETA_LAMP.get())
				.define('L', ModBlocks.ALPHA_LAMP.get())
				.define('B', ModBlocks.NEON_TUBE_BLOCK.get())
				.define('T', ModItems.NEON_TUBE.get())
				.pattern("TBT")
				.pattern("BLB")
				.pattern("TBT")
				.unlockedBy("has_alpha_lamp", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModBlocks.CLEAR_LAMP.get())
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CARBON_NANOTUBE.get(),64)
				.define('C', Items.COAL)
				.define('A', Items.AMETHYST_SHARD)
				.pattern("C C")
				.pattern(" A ")
				.pattern("C C")
				.unlockedBy("has_amethyst_for_carbon_nanotubes", inventoryTrigger(ItemPredicate.Builder.item()
						.of(Items.AMETHYST_SHARD)
						.build()))
				.save(pWriter);
		// Skipped: ModItems.CHORUS_FIBER.get()
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CLEAR_LAMP.get())
				.define('L', Items.REDSTONE_LAMP)
				.define('G', Items.GLASS)
				.define('R', Items.REDSTONE_TORCH)
				.pattern(" G ")
				.pattern("GLG")
				.pattern(" R ")
				.unlockedBy("has_redstone_lamp_for_clear_lamp", inventoryTrigger(ItemPredicate.Builder.item()
						.of(Items.REDSTONE_LAMP)
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CLEAR_SEA_LANTERN.get())
				.define('L', Items.SEA_LANTERN)
				.define('P', Items.PRISMARINE_CRYSTALS)
				.pattern("PPP")
				.pattern("PLP")
				.pattern("PPP")
				.unlockedBy("has_lantern_for_clear_lamp", inventoryTrigger(ItemPredicate.Builder.item()
						.of(Items.SEA_LANTERN)
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DEEP_OCEAN_LANTERN.get())
				.define('L', ModBlocks.OCEAN_LANTERN.get())
				.define('T', ModItems.XENON_TUBE.get())
				.pattern(" T ")
				.pattern("TLT")
				.pattern(" T ")
				.unlockedBy("has_ocean_lantern", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModBlocks.OCEAN_LANTERN.get())
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DEEP_SEA_LANTERN.get())
				.define('L', ModBlocks.CLEAR_SEA_LANTERN.get())
				.define('T', ModItems.ARGON_TUBE.get())
				.define('H', Items.HEART_OF_THE_SEA)
				.pattern("TTT")
				.pattern("TLT")
				.pattern("THT")
				.unlockedBy("has_clear_lantern", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModBlocks.CLEAR_SEA_LANTERN.get())
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DELTA_LAMP.get())
				.define('L', ModBlocks.GAMMA_LAMP.get())
				.define('B', ModBlocks.ARGON_TUBE_BLOCK.get())
				.define('T', ModItems.ARGON_TUBE.get())
				.pattern("TBT")
				.pattern("BLB")
				.pattern("TBT")
				.unlockedBy("has_delta_lamp", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModBlocks.GAMMA_LAMP.get())
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GLASS_TUBE.get(),16)
				.define('G', Items.GLASS)
				.pattern(" GG")
				.pattern("GGG")
				.pattern("GG ")
				.unlockedBy("has_glass", inventoryTrigger(ItemPredicate.Builder.item()
						.of(Items.GLASS)
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EPSILON_LAMP.get())
				.define('L', ModBlocks.DELTA_LAMP.get())
				.define('T', ModItems.KRYPTON_TUBE.get())
				.pattern(" T ")
				.pattern("TLT")
				.pattern(" T ")
				.unlockedBy("has_delta_lamp", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModBlocks.DELTA_LAMP.get())
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ETA_LAMP.get())
				.define('L', ModBlocks.ZETA_LAMP.get())
				.define('T', ModItems.RADON_TUBE.get())
				.pattern(" T ")
				.pattern("TLT")
				.pattern(" T ")
				.unlockedBy("has_zeta_lamp", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModBlocks.ZETA_LAMP.get())
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GAMMA_LAMP.get())
				.define('L', ModBlocks.BETA_LAMP.get())
				.define('T', ModItems.ARGON_TUBE.get())
				.pattern(" T ")
				.pattern("TLT")
				.pattern(" T ")
				.unlockedBy("has_beta_lamp", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModBlocks.BETA_LAMP.get())
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GLOWSTONE_CENTRIFUGE.get())
				.define('P', Blocks.PISTON)
				.define('T', ModItems.GLASS_TUBE.get())
				.define('I', lanthanum_ingot)
				.pattern("ITI")
				.pattern("IPI")
				.pattern("IPI")
				.unlockedBy("has_lanthanum_ingot", inventoryTrigger(ItemPredicate.Builder.item()
						.of(lanthanum_ingot)
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GLOWING_GLASS_BLOCK.get())
				.define('G', Items.GLOWSTONE_DUST)
				.define('#', Blocks.GLASS)
				.pattern(" G ")
				.pattern("G#G")
				.pattern(" G ")
				.unlockedBy("has_glowstone_dust", inventoryTrigger(ItemPredicate.Builder.item()
						.of(Items.GLOWSTONE_DUST)
						.build()))
				.save(pWriter);
		// Skipped: ModBlocks.JUNGLE_LANTERN.get()
		// Skipped: ModItems.STICKANDBOWL.get()
		SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
				Ingredient.of(ModItems.LANTHANUM_MESH.get()),
				Ingredient.of(Items.NETHERITE_INGOT),
				RecipeCategory.MISC,
				ModItems.NETHERITE_MESH.get())
				.unlocks(ModItems.NETHERITE_MESH.getId().toString(),inventoryTrigger(ItemPredicate.Builder.item()
						.of(Items.NETHERITE_INGOT)
						.build()))
				.save(pWriter, ModItems.NETHERITE_MESH.getId().toString());

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LANTHANUM_MESH.get())
				.define('N', lanthanum_nugget)
				.define('I', lanthanum_ingot)
				.pattern("N N")
				.pattern(" I ")
				.pattern("N N")
				.unlockedBy("has_lanthanum_ingot", inventoryTrigger(ItemPredicate.Builder.item()
						.of(lanthanum_ingot)
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.OCEAN_LANTERN.get())
				.define('L', ModBlocks.DEEP_SEA_LANTERN.get())
				.define('B', ModBlocks.KRYPTON_TUBE_BLOCK.get())
				.define('T', ModItems.KRYPTON_TUBE.get())
				.pattern("TBT")
				.pattern("BLB")
				.pattern("TBT")
				.unlockedBy("has_deep_sea_lantern", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModBlocks.DEEP_SEA_LANTERN.get())
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.OMEGA_LAMP.get())
				.define('L', ModBlocks.ETA_LAMP.get())
				.define('B', ModBlocks.RADON_TUBE_BLOCK.get())
				.define('S', Items.NETHER_STAR)
				.pattern("BLB")
				.pattern("LSL")
				.pattern("BLB")
				.unlockedBy("has_eta_lamp", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModBlocks.ETA_LAMP.get())
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.VANTA_BLACK.get(),16)
				.define('N', ModItems.CARBON_NANOTUBE.get())
				.pattern("NN")
				.pattern("NN")
				.unlockedBy("has_carbon_nanotube", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModItems.CARBON_NANOTUBE.get())
						.build()))
				.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ZETA_LAMP.get())
				.define('L', ModBlocks.EPSILON_LAMP.get())
				.define('B', ModBlocks.KRYPTON_BLOCK.get())
				.define('T', ModItems.KRYPTON_TUBE.get())
				.pattern("TBT")
				.pattern("BLB")
				.pattern("TBT")
				.unlockedBy("has_epsilon_lamp", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModBlocks.EPSILON_LAMP.get())
						.build()))
				.save(pWriter);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CURTAIN_BLOCK.get(),16)
				.define('N', ModItems.CARBON_NANOTUBE.get())
				.define('W', ItemTags.WOOL)
				.pattern("NN")
				.pattern("NW")
				.pattern("NW")
				.unlockedBy("has_carbon_nanotube", inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModItems.CARBON_NANOTUBE.get())
						.build()))
				.save(pWriter);

		oreBlasting(pWriter,Collections.singletonList(ModItems.RAW_LANTHANUM.get()), RecipeCategory.MISC,ModItems.LANTHANUM_INGOT.get(),1.0F, 200, "lanthanum_ingot");

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLOW_LICHEN_FIBER.get())
				.requires(Items.GLOW_LICHEN)
				.unlockedBy("has_"+ForgeRegistries.ITEMS.getKey(Items.GLOW_LICHEN).getPath(), inventoryTrigger(ItemPredicate.Builder.item()
						.of(Items.GLOW_LICHEN)
						.build()))
				.save(pWriter);

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LANTHANUM_INGOT.get())
				.requires(ModItems.LANTHANUM_NUGGET.get(),9)
				.unlockedBy("has_"+ModItems.LANTHANUM_INGOT.getId().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
						.of(lanthanum_ingot)
						.build()))
				.save(pWriter);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LANTHANUM_NUGGET.get(),9)
				.requires(lanthanum_ingot)
				.unlockedBy("has_"+ModItems.LANTHANUM_NUGGET.getId().getPath(), inventoryTrigger(ItemPredicate.Builder.item()
						.of(ModItems.LANTHANUM_NUGGET.get())
						.build()))
				.save(pWriter);

		// Group: Glowstone blocks
		HashMap<Block, Item> _glowstoneMap = new HashMap<>();
		_glowstoneMap.put(ModBlocks.NEON_BLOCK.get(),ModItems.NEON_DUST.get());
		_glowstoneMap.put(ModBlocks.ARGON_BLOCK.get(),ModItems.ARGON_DUST.get());
		_glowstoneMap.put(ModBlocks.KRYPTON_BLOCK.get(),ModItems.KRYPTON_DUST.get());
		_glowstoneMap.put(ModBlocks.XENON_BLOCK.get(),ModItems.XENON_DUST.get());
		_glowstoneMap.put(ModBlocks.RADON_BLOCK.get(),ModItems.RADON_DUST.get());
		_glowstoneMap.forEach((b,d)->{
			ShapedRecipeBuilder.shaped(RecipeCategory.MISC, b)
					.define('D', d)
					.pattern("DD")
					.pattern("DD")
					.unlockedBy("has_"+ Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(d)).getPath(), inventoryTrigger(ItemPredicate.Builder.item()
							.of(d)
							.build()))
					.save(pWriter);
		});
		// End Group

		// Group: Block of Tubes
		HashMap<Block, Item> _tubeBlocksMap = new HashMap<>();
		_tubeBlocksMap.put(ModBlocks.NEON_TUBE_BLOCK.get(),ModItems.NEON_TUBE.get());
		_tubeBlocksMap.put(ModBlocks.ARGON_TUBE_BLOCK.get(),ModItems.ARGON_TUBE.get());
		_tubeBlocksMap.put(ModBlocks.KRYPTON_TUBE_BLOCK.get(),ModItems.KRYPTON_TUBE.get());
		_tubeBlocksMap.put(ModBlocks.XENON_TUBE_BLOCK.get(),ModItems.XENON_TUBE.get());
		_tubeBlocksMap.put(ModBlocks.RADON_TUBE_BLOCK.get(),ModItems.RADON_TUBE.get());
		_tubeBlocksMap.forEach((b,t)->{
			ShapedRecipeBuilder.shaped(RecipeCategory.MISC, b)
					.define('T', t)
					.pattern("TT")
					.pattern("TT")
					.unlockedBy("has_"+ Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(t)).getPath(), inventoryTrigger(ItemPredicate.Builder.item()
							.of(t)
							.build()))
					.save(pWriter);
		});
		// End Group

		// Group: Tubes
		HashMap<Item, Item> _tubesMap = new HashMap<>();
		_tubesMap.put(ModItems.NEON_TUBE.get(),ModItems.NEON_DUST.get());
		_tubesMap.put(ModItems.ARGON_TUBE.get(),ModItems.ARGON_DUST.get());
		_tubesMap.put(ModItems.KRYPTON_TUBE.get(),ModItems.KRYPTON_DUST.get());
		_tubesMap.put(ModItems.XENON_TUBE.get(),ModItems.XENON_DUST.get());
		_tubesMap.put(ModItems.RADON_TUBE.get(),ModItems.RADON_DUST.get());
		_tubesMap.forEach((b,t)->{
			ShapedRecipeBuilder.shaped(RecipeCategory.MISC, b)
					.define('T', t)
					.define('R', ModItems.GLASS_TUBE.get())
					.pattern(" T ")
					.pattern("TRT")
					.pattern(" T ")
					.unlockedBy("has_"+ Objects.requireNonNull(ModItems.GLASS_TUBE.getId()).getPath(), inventoryTrigger(ItemPredicate.Builder.item()
							.of(t)
							.build()))
					.save(pWriter);
		});
		// End Group

		// Group: Filters
		HashMap<Item, Item> _filtersMap = new HashMap<>();
		_filtersMap.put(Items.STRING,ModItems.BASIC_FILTER.get());
		_filtersMap.put(Items.PHANTOM_MEMBRANE,ModItems.NEON_FILTER.get());
		_filtersMap.put(ModItems.GLOW_LICHEN_FIBER.get(),ModItems.ARGON_FILTER.get());
		_filtersMap.put(ModItems.LANTHANUM_MESH.get(),ModItems.KRYPTON_FILTER.get());
		_filtersMap.put(ModItems.NETHERITE_MESH.get(),ModItems.XENON_FILTER.get());
		_filtersMap.put(ModItems.CHORUS_FIBER.get(),ModItems.RADON_FILTER.get());
		_filtersMap.forEach((i,o)->{
			ShapedRecipeBuilder.shaped(RecipeCategory.MISC, o)
					.define('#', i)
					.define('B', lanthanum_ingot)
					.pattern(" B ")
					.pattern("B#B")
					.pattern(" B ")
					.unlockedBy("has_"+Objects.requireNonNull(lanthanum_ingot.registry().location()).getPath(), inventoryTrigger(ItemPredicate.Builder.item()
							.of(lanthanum_ingot)
							.build()))
					.save(pWriter);
		});
		// End Group

		// Group: Pile to Dust
		HashMap<Item, Item> _pileToDustMap = new HashMap<>();
		_pileToDustMap.put(ModItems.NEON_PILE.get(),ModItems.NEON_DUST.get());
		_pileToDustMap.put(ModItems.ARGON_PILE.get(),ModItems.ARGON_DUST.get());
		_pileToDustMap.put(ModItems.KRYPTON_PILE.get(),ModItems.KRYPTON_DUST.get());
		_pileToDustMap.put(ModItems.XENON_PILE.get(),ModItems.XENON_DUST.get());
		_pileToDustMap.put(ModItems.RADON_PILE.get(),ModItems.RADON_DUST.get());
		_pileToDustMap.put(ModItems.LANTHANUM_PILE.get(),ModItems.LANTHANUM_DUST.get());
		_pileToDustMap.forEach((i,o)->{
			ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, o)
					.requires(i,4)
					.unlockedBy("has_"+Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(i)).getPath(), inventoryTrigger(ItemPredicate.Builder.item()
							.of(lanthanum_ingot)
							.build()))
					.save(pWriter);
		});
		// End Group

		// Group: Dust to Pile
		_pileToDustMap.forEach((o,i)->{
			ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, o,4)
					.requires(i)
					.unlockedBy("has_"+Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(i)).getPath(), inventoryTrigger(ItemPredicate.Builder.item()
							.of(lanthanum_ingot)
							.build()))
					.save(pWriter);
		});
		// End Group
	}
}
