package tk.dczippl.lightestlamp.machine.gasextractor;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import tk.dczippl.lightestlamp.init.ModItems;
import tk.dczippl.lightestlamp.util.TwoSlotRecipe;

import java.util.ArrayList;
import java.util.List;

public class GasExtractorRecipes
{
    private static final GasExtractorRecipes base = new GasExtractorRecipes();

    private List<TwoSlotRecipe> RECIPES = new ArrayList<>();

    public static GasExtractorRecipes instance()
    {
        return base;
    }

    private GasExtractorRecipes()
    {
        addRecipe(new ItemStack(Blocks.GLOWSTONE),new ItemStack(ModItems.EMPTY_ROD),new ItemStack(ModItems.NEON_ROD),new ItemStack(ModItems.ARGON_ROD));
        addRecipe(new ItemStack(ModItems.NEON_ROD),new ItemStack(ModItems.NEON_ROD),new ItemStack(ModItems.EMPTY_ROD),new ItemStack(ModItems.ARGON_ROD));
    }

    public void addRecipe(ItemStack input, ItemStack second_input, ItemStack output, ItemStack extra_output)
    {
        TwoSlotRecipe tsr = new TwoSlotRecipe();
        tsr.input = input;
        tsr.catalyst = second_input;
        tsr.output = output;
        tsr.extra_output = extra_output;
        RECIPES.add(tsr);
    }

    public ItemStack getResult(ItemStack input, ItemStack second_input)
    {
        for (TwoSlotRecipe Recipe : RECIPES)
        {
            if (Recipe.input.isItemEqual(input)&& Recipe.catalyst.isItemEqual(second_input))
                return Recipe.output;
        }
        return ItemStack.EMPTY;
    }

    public ItemStack getExtraResult(ItemStack input, ItemStack second_input)
    {
        for (TwoSlotRecipe Recipe : RECIPES)
        {
            if (Recipe.input.isItemEqual(input)&& Recipe.catalyst.isItemEqual(second_input))
                return Recipe.extra_output;
        }
        return ItemStack.EMPTY;
    }

    public int[] getInputsAmount(ItemStack input, ItemStack second_input)
    {
        for (TwoSlotRecipe Recipe : RECIPES)
        {
            if (Recipe.input.isItemEqual(input)&& Recipe.catalyst.isItemEqual(second_input))
                return new int[]{Recipe.input.getCount(),Recipe.catalyst.getCount()};
        }
        return null;
    }

    List<List<ItemStack>> inputs;
    List<List<ItemStack>> outputs;

    public List<List<ItemStack>> getOutputs()
    {
        List<ItemStack> ioutputs = new ArrayList<>();
        ioutputs.add(new ItemStack(ModItems.NEON_ROD));
        ioutputs.add(new ItemStack(ModItems.KRYPTON_ROD));
        outputs.add(ioutputs);
        return outputs;
    }

    public List<List<ItemStack>> getInputs()
    {
        List<ItemStack> iinputs = new ArrayList<>();
        iinputs.add(new ItemStack(ModItems.EMPTY_ROD));
        iinputs.add(new ItemStack(Blocks.GLOWSTONE));
        inputs.add(iinputs);
        return inputs;
    }
}