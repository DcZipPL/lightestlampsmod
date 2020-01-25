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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import tk.dczippl.lightestlamp.Reference;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/*public class GasCentrifugeRecipeCategory implements IRecipeCategory<GasCentrifugeRecipeJEI>
{
    private final IDrawable background;

    private final List<List<ItemStack>> inputs;
    private final ItemStack output;

    public GasCentrifugeRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, "textures/gui/fast_furnace.png");
        background = guiHelper.createDrawable(location, 3, 18, 170, 30);
        this.inputs = Collections.singletonList(Collections.singletonList(recipe.getInput().copy()));
        this.output = recipe.getOutput().copy();
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(Reference.MOD_ID);
    }

    @Override
    public Class<? extends GasCentrifugeRecipeJEI> getRecipeClass()
    {
        return null;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return "Fast Furnace";
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon()
    {
        return null;
    }

    @Override
    public void setIngredients(GasCentrifugeRecipeJEI gasCentrifugeRecipeJEI, IIngredients iIngredients)
    {
        iIngredients.setOutput(VanillaTypes.ITEM, output);
        iIngredients.setInputLists(VanillaTypes.ITEM, inputs);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, GasCentrifugeRecipeJEI recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 6, 6);
        guiItemStacks.init(3, false, 114, 6);

        List<ItemStack> inputs = ingredients.getInputs(VanillaTypes.ITEM).get(0);
        List<ItemStack> outputs = ingredients.getOutputs(VanillaTypes.ITEM).get(0);

        guiItemStacks.set(0, inputs);
        guiItemStacks.set(3, outputs);
    }
}*/