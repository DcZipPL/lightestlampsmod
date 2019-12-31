package tk.dczippl.lightestlamp.datagen;

//import al132.chemlib.items.ModItems;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.init.ModBlocks;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.ANTI_LAMP)
                .patternLine("AAA")
                .patternLine("ACA")
                .patternLine("AAA")
                .key('A', Items.NETHER_WART)
                .key('C', ModBlocks.CLEAR_LAMP)
                .setGroup(Reference.MOD_ID)
                .addCriterion("netherrack", InventoryChangeTrigger.Instance.forItems(Blocks.NETHERRACK))
                .build(consumer);
        //ShapedRecipeBuilder.shapedRecipe(Items.GLOWSTONE)
        //        .patternLine("NN")
        //        .patternLine("AK")
        //        .key('N', ModItems.neon)
        //        .key('A', ModItems.argon)
        //        .key('K', ModItems.krypton)
        //        .setGroup(Reference.MOD_ID)
        //        .addCriterion("neonrec", InventoryChangeTrigger.Instance.forItems(ModItems.neon))
        //        .build(consumer);
    }
}
