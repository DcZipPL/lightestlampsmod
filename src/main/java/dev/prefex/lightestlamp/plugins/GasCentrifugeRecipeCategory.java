package dev.prefex.lightestlamp.plugins;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.init.ModBlocks;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static dev.prefex.lightestlamp.plugins.JEIPlugin.GAS_CENTRIFUGE;
import static dev.prefex.lightestlamp.plugins.JEIPlugin.JEI_RECIPE;

public record GasCentrifugeRecipeCategory(IGuiHelper guiHelper) implements IRecipeCategory<GasCentrifugeRecipe> {

    @Override
    public Component getTitle() {
        return new TranslatableComponent("container.lightestlamp.glowstone_centrifuge");
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createDrawable(new ResourceLocation(Util.MOD_ID,"textures/gui/container/gas_centrifuge.png"),8,8,142,70);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, GasCentrifugeRecipe recipe, IFocusGroup focuses) {
        IRecipeCategory.super.setRecipe(builder, recipe, focuses);
        builder.addSlot(RecipeIngredientRole.CATALYST, 8, 27).addIngredients(recipe.filter());
        builder.addSlot(RecipeIngredientRole.INPUT, 33, 27).addIngredients(recipe.input());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 91, 11).addIngredient(VanillaTypes.ITEM_STACK,recipe.output().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 119, 11).addIngredient(VanillaTypes.ITEM_STACK,recipe.output().get(1));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 91, 44).addIngredient(VanillaTypes.ITEM_STACK,recipe.output().get(2));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 119, 44).addIngredient(VanillaTypes.ITEM_STACK,recipe.output().get(3));
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.GLOWSTONE_CENTRIFUGE.get()));
    }

    // TODO: Remove this when you can skip this
    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(Util.MOD_ID, GAS_CENTRIFUGE);
    }

    // TODO: Remove this when you can skip this
    @SuppressWarnings("removal")
    @Override
    public Class<? extends GasCentrifugeRecipe> getRecipeClass() {
        return GasCentrifugeRecipe.class;
    }

    @Override
    public RecipeType<GasCentrifugeRecipe> getRecipeType() {
        return JEI_RECIPE;
    }
}
