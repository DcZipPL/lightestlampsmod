package tk.dczippl.lightestlamp.plugins;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.sun.java.accessibility.util.Translator;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class GasCentrifugeRecipeJEI
{
    private final List<List<ItemStack>> inputs;
    private final ItemStack output;

    public GasCentrifugeRecipeJEI(GasCentrifugeRecipe recipe) {
        this.inputs = Collections.singletonList(Collections.singletonList(recipe.getInput().copy()));
        this.output = recipe.getOutput().copy();
    }

    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.ITEM, output);
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);
    }
}