package dev.prefex.lightestlamp.plugins;

import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.component.IngredientComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemStackComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import net.minecraft.world.item.ItemStack;

public final class LightestLampKubeJSPlugin implements KubeJSPlugin {
    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.namespace("lightestlamp").register("glowstone_centrifuge", new RecipeSchema(
                IngredientComponent.INGREDIENT.inputKey("filter"),
                IngredientComponent.INGREDIENT.inputKey("input"),
                ItemStackComponent.OPTIONAL_ITEM_STACK.outputKey("first").optional(ItemStack.EMPTY),
                ItemStackComponent.OPTIONAL_ITEM_STACK.outputKey("second").optional(ItemStack.EMPTY),
                ItemStackComponent.OPTIONAL_ITEM_STACK.outputKey("third").optional(ItemStack.EMPTY),
                ItemStackComponent.OPTIONAL_ITEM_STACK.outputKey("fourth").optional(ItemStack.EMPTY)
        ));
    }
}
