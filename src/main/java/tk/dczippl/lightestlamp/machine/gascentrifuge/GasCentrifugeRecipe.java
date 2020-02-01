package tk.dczippl.lightestlamp.machine.gascentrifuge;

import com.google.common.base.Preconditions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TranslationTextComponent;
import tk.dczippl.lightestlamp.init.ModItems;
import tk.dczippl.lightestlamp.util.TheoreticalFluid;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class GasCentrifugeRecipe
{
    protected static List<GasCentrifugeRecipe> recipes = new ArrayList<GasCentrifugeRecipe>();
    public static final GasCentrifugeRecipe basic = new GasCentrifugeRecipe(
            new ItemStack(ModItems.BASIC_FILTER), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.NEON_DUST),new ItemStack(ModItems.ARGON_DUST),new ItemStack(ModItems.KRYPTON_PILE),ItemStack.EMPTY,new TheoreticalFluid(TheoreticalFluidTypes.Bromine,5));
    public static final GasCentrifugeRecipe neon = new GasCentrifugeRecipe(
            new ItemStack(ModItems.NEON_FILTER), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.NEON_DUST),new ItemStack(ModItems.NEON_DUST),new ItemStack(ModItems.NEON_PILE),new ItemStack(ModItems.ARGON_PILE),new TheoreticalFluid(TheoreticalFluidTypes.Bromine,10));
    public static final GasCentrifugeRecipe argon = new GasCentrifugeRecipe(
            new ItemStack(ModItems.ARGON_FILTER), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.ARGON_DUST),new ItemStack(ModItems.ARGON_DUST),new ItemStack(ModItems.NEON_PILE),ItemStack.EMPTY,new TheoreticalFluid(TheoreticalFluidTypes.Bromine,2));
    public static final GasCentrifugeRecipe krypton = new GasCentrifugeRecipe(
            new ItemStack(ModItems.KRYPTON_FILTER), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.ARGON_PILE),new ItemStack(ModItems.KRYPTON_DUST),new ItemStack(ModItems.KRYPTON_PILE),ItemStack.EMPTY,new TheoreticalFluid(TheoreticalFluidTypes.Bromine,15));
    public static final GasCentrifugeRecipe bromine = new GasCentrifugeRecipe(
            new ItemStack(ModItems.BROMINE_FILTER), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.BORON_PILE),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,new TheoreticalFluid(TheoreticalFluidTypes.Bromine,50));

    public GasCentrifugeRecipe(ItemStack top_input, ItemStack bottom_input, ItemStack right_up_output, ItemStack left_up_output, ItemStack right_bottom_output, ItemStack left_bottom_output, TheoreticalFluid liquid_output)
    {
        this.top_input = top_input;
        //this.bottom_input = bottom_input;
        this.right_up_output = right_up_output;
        this.left_up_output = left_up_output;
        this.right_bottom_output = right_bottom_output;
        this.left_bottom_output = left_bottom_output;
        this.liquid_output = liquid_output;
    }

    public static TheoreticalFluid getFluid(ItemStack input)
    {
        for (GasCentrifugeRecipe recipe : getRecipes())
        {
            if (input.getItem() == recipe.top_input.getItem())
            {
                return recipe.liquid_output;
            }
        }
        return null;
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
        try
        {
            if (al132.chemlib.ChemLib.MODID!=null)
            {
                recipes.add(new GasCentrifugeRecipe(
                        new ItemStack(ModItems.BASIC_FILTER), new ItemStack(Items.GLOWSTONE), new ItemStack(al132.chemlib.items.ModItems.neon,4), new ItemStack(al132.chemlib.items.ModItems.argon,4), new ItemStack(al132.chemlib.items.ModItems.krypton,1), ItemStack.EMPTY,
                        new TheoreticalFluid(TheoreticalFluidTypes.Bromine, 5)));
                recipes.add(new GasCentrifugeRecipe(
                        new ItemStack(ModItems.NEON_FILTER), new ItemStack(Items.GLOWSTONE), new ItemStack(al132.chemlib.items.ModItems.neon,8), new ItemStack(al132.chemlib.items.ModItems.neon,1), new ItemStack(al132.chemlib.items.ModItems.argon,1), ItemStack.EMPTY,
                        new TheoreticalFluid(TheoreticalFluidTypes.Bromine, 10)));
                recipes.add(new GasCentrifugeRecipe(
                        new ItemStack(ModItems.ARGON_FILTER), new ItemStack(Items.GLOWSTONE), new ItemStack(al132.chemlib.items.ModItems.argon,8), new ItemStack(al132.chemlib.items.ModItems.neon,1), ItemStack.EMPTY, ItemStack.EMPTY,
                        new TheoreticalFluid(TheoreticalFluidTypes.Bromine, 2)));
                recipes.add(new GasCentrifugeRecipe(
                        new ItemStack(ModItems.KRYPTON_FILTER), new ItemStack(Items.GLOWSTONE), new ItemStack(al132.chemlib.items.ModItems.krypton,5),new ItemStack(al132.chemlib.items.ModItems.argon,1), ItemStack.EMPTY, ItemStack.EMPTY,
                        new TheoreticalFluid(TheoreticalFluidTypes.Bromine, 15)));
                recipes.add(new GasCentrifugeRecipe(
                        new ItemStack(ModItems.BROMINE_FILTER), new ItemStack(Items.GLOWSTONE), new ItemStack(ModItems.BORON_PILE), ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
                        new TheoreticalFluid(TheoreticalFluidTypes.Bromine,50)));
            }
        }
        catch (NoClassDefFoundError ignore)
        {
            recipes.add(basic);
            recipes.add(neon);
            recipes.add(argon);
            recipes.add(krypton);
            recipes.add(bromine);
        }
        return recipes;
    }

    public ItemStack top_input;
    public ItemStack bottom_input;
    public ItemStack right_up_output;
    public ItemStack left_up_output;
    public ItemStack right_bottom_output;
    public ItemStack left_bottom_output;
    public TheoreticalFluid liquid_output;

    public enum TheoreticalFluidTypes
    {
        Water,
        Lava,
        Bromine
    }
}