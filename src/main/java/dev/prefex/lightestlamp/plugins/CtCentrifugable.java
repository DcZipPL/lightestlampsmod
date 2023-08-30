package dev.prefex.lightestlamp.plugins;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientCraftTweaker;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import dev.prefex.lightestlamp.init.ModMiscs;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeBlockEntity;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Optional;

@IRecipeHandler.For(GasCentrifugeRecipe.class)
@ZenRegister
@ZenCodeType.Name("lightestlamp.GlowstoneCentrifuge")
public class CtCentrifugable implements IRecipeManager, IRecipeHandler<GasCentrifugeRecipe> {
    @ZenCodeType.Method
    public void addCentrifugable(int processTime, IIngredient input){
        for (var item : input.getItems()){
            GasCentrifugeBlockEntity.addedCentrifugables.put(item.getDefinition(),processTime);
        }
    }

    @Override
    public String dumpToCommandString(IRecipeManager manager, GasCentrifugeRecipe recipe) {
        return manager.getCommandString() + recipe.id() + recipe.output() + "[" + recipe.filter() + recipe.input() + "]";
    }

    @Override
    public RecipeType<?> getRecipeType() {
        return ModMiscs.GLOWSTONE_CENTRIFUGE_RECIPE_TYPE.get();
    }

    // TODO: Check this
    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super GasCentrifugeRecipe> manager, GasCentrifugeRecipe firstRecipe, U secondRecipe) {
        return false;
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super GasCentrifugeRecipe> manager, GasCentrifugeRecipe recipe) {
        return Optional.empty();
    }

    @Override
    public Optional<GasCentrifugeRecipe> recompose(IRecipeManager<? super GasCentrifugeRecipe> manager, ResourceLocation name, IDecomposedRecipe recipe) {
        return Optional.empty();
    }
}
