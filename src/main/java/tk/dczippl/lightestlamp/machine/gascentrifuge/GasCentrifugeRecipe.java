package tk.dczippl.lightestlamp.machine.gascentrifuge;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import tk.dczippl.lightestlamp.init.ModItems;

import java.util.ArrayList;
import java.util.List;

public class GasCentrifugeRecipe
{
    protected static List<GasCentrifugeRecipe> recipes = new ArrayList<GasCentrifugeRecipe>();
    public static final GasCentrifugeRecipe basic = new GasCentrifugeRecipe(
            new ItemStack(ModItems.BASIC_FILTER.get()), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.NEON_DUST.get()),new ItemStack(ModItems.ARGON_DUST.get()),new ItemStack(ModItems.KRYPTON_PILE.get()),ItemStack.EMPTY);
    public static final GasCentrifugeRecipe neon = new GasCentrifugeRecipe(
            new ItemStack(ModItems.NEON_FILTER.get()), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.NEON_DUST.get(),2),new ItemStack(ModItems.NEON_PILE.get()),new ItemStack(ModItems.ARGON_PILE.get()),ItemStack.EMPTY);
    public static final GasCentrifugeRecipe argon = new GasCentrifugeRecipe(
            new ItemStack(ModItems.ARGON_FILTER.get()), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.ARGON_DUST.get(),2),new ItemStack(ModItems.NEON_PILE.get(),2),new ItemStack(ModItems.XENON_PILE.get(),1),ItemStack.EMPTY);
    public static final GasCentrifugeRecipe krypton = new GasCentrifugeRecipe(
            new ItemStack(ModItems.KRYPTON_FILTER.get()), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.ARGON_PILE.get(),3),new ItemStack(ModItems.KRYPTON_DUST.get()),new ItemStack(ModItems.KRYPTON_PILE.get(),2),new ItemStack(ModItems.XENON_PILE.get(),2));
    public static final GasCentrifugeRecipe xenon = new GasCentrifugeRecipe(
            new ItemStack(ModItems.XENON_FILTER.get()), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.KRYPTON_PILE.get(),2),new ItemStack(ModItems.XENON_DUST.get()),new ItemStack(ModItems.XENON_PILE.get(),2),new ItemStack(ModItems.RADON_PILE.get(),1));
    public static final GasCentrifugeRecipe radon = new GasCentrifugeRecipe(
            new ItemStack(ModItems.RADON_FILTER.get()), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.XENON_PILE.get(),2),new ItemStack(ModItems.RADON_PILE.get(),3),ItemStack.EMPTY,ItemStack.EMPTY);
    public static final GasCentrifugeRecipe bromine = new GasCentrifugeRecipe(
            new ItemStack(ModItems.BROMINE_FILTER.get()), new ItemStack(Items.GLOWSTONE),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY);

    public GasCentrifugeRecipe(ItemStack top_input, ItemStack bottom_input, ItemStack right_up_output, ItemStack left_up_output, ItemStack right_bottom_output, ItemStack left_bottom_output)
    {
        this.top_input = top_input;
        //this.bottom_input = bottom_input;
        this.right_up_output = right_up_output;
        this.left_up_output = left_up_output;
        this.right_bottom_output = right_bottom_output;
        this.left_bottom_output = left_bottom_output;
    }

    public static ItemStack[] getRecipeOutputs(ItemStack input)
    {
        for (GasCentrifugeRecipe recipe : getRecipes())
        {
            if (input.getItem() == recipe.top_input.getItem())
            {
                ItemStack[] items = new ItemStack[]{recipe.right_up_output, recipe.left_up_output, recipe.right_bottom_output, recipe.left_bottom_output};
                return items;
            }
        }
        ItemStack[] items = new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
        return items;
    }

    public static List<GasCentrifugeRecipe> getRecipes()
    {
        recipes.add(basic);
        recipes.add(neon);
        recipes.add(argon);
        recipes.add(krypton);
        recipes.add(xenon);
        recipes.add(radon);
        recipes.add(bromine);
        return recipes;
    }

    public ItemStack top_input;
    public ItemStack bottom_input;
    public ItemStack right_up_output;
    public ItemStack left_up_output;
    public ItemStack right_bottom_output;
    public ItemStack left_bottom_output;
}