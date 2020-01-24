package tk.dczippl.lightestlamp.machine.gascentrifuge;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import tk.dczippl.lightestlamp.init.ModItems;
import tk.dczippl.lightestlamp.util.TheoreticalFluid;

public class GasCentrifugeRecipe
{
    public static final BasicGasCentrifugeRecipe glowstone = new BasicGasCentrifugeRecipe(
            new ItemStack(ModItems.EMPTY_ROD), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.NEON_DUST),new ItemStack(ModItems.ARGON_DUST),new ItemStack(ModItems.KRYPTON_DUST),ItemStack.EMPTY,new TheoreticalFluid(TheoreticalFluidTypes.Bromine,5));

    public static class BasicGasCentrifugeRecipe
    {
        public BasicGasCentrifugeRecipe(ItemStack top_input, ItemStack bottom_input, ItemStack right_up_output, ItemStack left_up_output, ItemStack right_bottom_output, ItemStack left_bottom_output, TheoreticalFluid liquid_output)
        {
            this.top_input = top_input;
            //this.bottom_input = bottom_input;
            this.right_up_output = right_up_output;
            this.left_up_output = left_up_output;
            this.right_bottom_output = right_bottom_output;
            this.left_bottom_output = left_bottom_output;
            this.liquid_output = liquid_output;
        }

        public static ItemStack[] getRecipeOutputs(ItemStack input)
        {
            if (input.getItem() == GasCentrifugeRecipe.glowstone.top_input.getItem())
            {
                ItemStack[] items = new ItemStack[]{GasCentrifugeRecipe.glowstone.right_up_output, GasCentrifugeRecipe.glowstone.left_up_output, GasCentrifugeRecipe.glowstone.right_bottom_output, GasCentrifugeRecipe.glowstone.left_bottom_output};
                return items;
            }
            else
            {
                ItemStack[] items = new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
                return items;
            }
        }

        public ItemStack top_input;
        public ItemStack bottom_input;
        public ItemStack right_up_output;
        public ItemStack left_up_output;
        public ItemStack right_bottom_output;
        public ItemStack left_bottom_output;
        public TheoreticalFluid liquid_output;
    }

    public enum TheoreticalFluidTypes
    {
        Water,
        Lava,
        Bromine
    }
}