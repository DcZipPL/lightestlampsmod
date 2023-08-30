package dev.prefex.lightestlamp.datagen.tags;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModTagsProvider {
	public static class ModBlockTagsProvider extends BlockTagsProvider {

		public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
			super(output, lookupProvider, modId, existingFileHelper);
		}

		@Override
		protected void addTags(HolderLookup.@NotNull Provider pProvider) {
			this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.LANTHANUM_ORE.get());
			this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.LANTHANUM_ORE.get());
			this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.GLOWSTONE_CENTRIFUGE.get());
		}
	}
	public static class ModItemTagsProvider extends ItemTagsProvider {
		public ModItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, String modId, @Nullable ExistingFileHelper existingFileHelper) {
			super(pOutput, pLookupProvider, pBlockTags, modId, existingFileHelper);
		}

		@Override
		protected void addTags(HolderLookup.@NotNull Provider pProvider) {

		}
	}
}
