package tk.dczippl.lightestlamp.plugins;

import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeScreen;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class GasCentrifugeRecipeCategory implements IRecipeCategory<GasCentrifugeRecipeJEI>
{
    IDrawable bg;
    IDrawable icon;

    public GasCentrifugeRecipeCategory(IGuiHelper helper)
    {
        bg = helper.drawableBuilder(GasCentrifugeScreen.texture, 8, 6, 164, 74).addPadding(0, 16, 0, 0).build();
        icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.GAS_EXTRACTOR));
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(Reference.MOD_ID, JEIPlugin.GAS_CENTRIFUGE);
    }

    @Override
    public String getTitle() {
        return I18n.format("jei.lightestlamp.gascentrifuge");
    }

    @Override
    public IDrawable getBackground() {
        return bg;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, GasCentrifugeRecipeJEI wrap, IIngredients ing) {
        IGuiItemStackGroup stacks = layout.getItemStacks();
        stacks.init(0, true, 7, 28);
        stacks.init(1, true, 32, 28);
        stacks.init(2, false, 90, 12);
        stacks.init(3, false, 118, 12);
        stacks.init(4, false, 90, 44);
        stacks.init(5, false, 118, 44);
        stacks.set(ing);
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public Class<? extends GasCentrifugeRecipeJEI> getRecipeClass() {
        return GasCentrifugeRecipeJEI.class;
    }

    @Override
    public void setIngredients(GasCentrifugeRecipeJEI wrapper, IIngredients ing) {
        wrapper.getIngredients(ing);
    }

    @Override
    public void draw(GasCentrifugeRecipeJEI recipe, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, mouseX, mouseY);
        recipe.drawInfo(Minecraft.getInstance(), 0, 40, mouseX, mouseY);
    }
}