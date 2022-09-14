package dev.prefex.lightestlamp.plugins;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.init.ModBlocks;
import dev.prefex.lightestlamp.init.ModItems;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static dev.prefex.lightestlamp.plugins.JEIPlugin.GAS_CENTRIFUGE;

public record GasCentrifugeRecipeCategory(IGuiHelper guiHelper) implements IRecipeCategory<GasCentrifugeRecipe> {

    @Override
    public Component getTitle() {
        return new TranslatableComponent("container.lightestlamp.glowstone_centrifuge");
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createDrawable(new ResourceLocation(Util.MOD_ID,"textures/gui/container/gas_centrifuge.png"),0,0,180,120);
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.GLOWSTONE_CENTRIFUGE.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(Util.MOD_ID, GAS_CENTRIFUGE);
    }

    @Override
    public Class<? extends GasCentrifugeRecipe> getRecipeClass() {
        return GasCentrifugeRecipe.class;
    }
}
