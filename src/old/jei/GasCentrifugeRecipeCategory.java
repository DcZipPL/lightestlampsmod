package tk.dczippl.lightestlamp.jei;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.machine.gasextractor.GasExtractorGui;
import tk.dczippl.lightestlamp.machine.gasextractor.GasExtractorRecipes;

public class GasCentrifugeRecipeCategory// implements IRecipeCategory<GasExtractorRecipes>
{
    /*private static final int slot0 = 0;
    private static final int slot1 = 1;
    private static final int outputSlot0 = 2;
    private static final int outputSlot1 = 4;
    private final ResourceLocation location;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slotDrawable;
    private final String localizedName;
    private final IDrawableAnimated arrow;

    public GasCentrifugeRecipeCategory(IGuiHelper guiHelper) {
        this.location = new ResourceLocation(Reference.MOD_ID,"gascentrifuge");
        this.background = guiHelper.drawableBuilder(location, 0, 0, 64, 60).addPadding(1, 0, 0, 50).build();
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.GAS_EXTRACTOR));
        this.localizedName = "Gas Centrifuge";
        this.arrow = guiHelper.drawableBuilder(location, 64, 0, 9, 28).buildAnimated(400, IDrawableAnimated.StartDirection.TOP, false);
        this.slotDrawable = guiHelper.getSlotDrawable();
    }

    @Override
    public ResourceLocation getUid()
    {
        return location;
    }

    @Override
    public Class getRecipeClass()
    {
        return GasExtractorRecipes.class;
    }

    @Override
    public String getTitle()
    {
        return localizedName;
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setIngredients(GasExtractorRecipes recipe, IIngredients iIngredients)
    {
        iIngredients.setInputLists(VanillaTypes.ITEM, recipe.getInputs());
        iIngredients.setOutputLists(VanillaTypes.ITEM, recipe.getOutputs());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, GasExtractorRecipes recipe, IIngredients iIngredients)
    {
        IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
        itemStacks.init(0, true, 0, 36);
        itemStacks.init(1, true, 23, 43);
        itemStacks.init(2, false, 46, 36);
        itemStacks.init(3, false, 23, 2);
        itemStacks.setBackground(4, this.slotDrawable);
        itemStacks.set(iIngredients);
    }*/
}