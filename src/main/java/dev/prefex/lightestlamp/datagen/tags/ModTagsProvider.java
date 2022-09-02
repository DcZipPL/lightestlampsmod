package dev.prefex.lightestlamp.datagen.tags;

import dev.prefex.lightestlamp.Reference;
import dev.prefex.lightestlamp.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModTagsProvider {
	public static class ModBlockTagsProvider extends BlockTagsProvider {
		public ModBlockTagsProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
			super(pGenerator, Reference.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.MONAZITE_ORE.get());
			this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.MONAZITE_ORE.get());
			this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.GAS_EXTRACTOR.get());
		}
	}
	public static class ModItemTagsProvider extends ItemTagsProvider {
		public ModItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
			super(pGenerator, pBlockTagsProvider, Reference.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags() {
		}
	}
}
