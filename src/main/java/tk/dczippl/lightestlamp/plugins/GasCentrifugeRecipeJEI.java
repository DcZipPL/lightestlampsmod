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
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tk.dczippl.lightestlamp.Main;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModItems;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GasCentrifugeRecipeJEI
{
    public static final ArrayList<ItemStack> GLOWSTONES = new ArrayList<>();

    ItemStack modifier;
    ItemStack[] outputs;
    String[] tooltips;

    public GasCentrifugeRecipeJEI(ItemStack modifier, String... tooltips) {
        this.modifier = modifier;
        this.outputs = GasCentrifugeRecipe.getRecipeOutputs(modifier);
        this.tooltips =  tooltips;
    }

    public void getIngredients(IIngredients ingredients) {
        GLOWSTONES.add(new ItemStack(Items.GLOWSTONE_DUST));
        GLOWSTONES.add(new ItemStack(Items.GLOWSTONE));

        //Mekanism compatibility
        Tag<Item> refined_glowstones = ItemTags.getCollection().get(new ResourceLocation("forge:ingots/refined_glowstone"));
        if (refined_glowstones!=null)
            refined_glowstones.getAllElements().forEach(item -> {
                GLOWSTONES.add(new ItemStack(item));
            });
        Tag<Item> refined_glowstones_block = ItemTags.getCollection().get(new ResourceLocation("forge:storage_blocks/refined_glowstone"));
        if (refined_glowstones_block!=null)
            refined_glowstones_block.getAllElements().forEach(item -> {
                GLOWSTONES.add(new ItemStack(item));
            });
        Tag<Item> refined_glowstones_nugget = ItemTags.getCollection().get(new ResourceLocation("forge:nuggets/refined_glowstone"));
        if (refined_glowstones_nugget!=null)
            refined_glowstones_nugget.getAllElements().forEach(item -> {
                GLOWSTONES.add(new ItemStack(item));
            });
        Tag<Item> glowstone_blocks = ItemTags.getCollection().get(new ResourceLocation("forge:storage_blocks/glowstone"));
        if (glowstone_blocks!=null)
            glowstone_blocks.getAllElements().forEach(item -> {
                GLOWSTONES.add(new ItemStack(item));
            });
        ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(Collections.singletonList(modifier), GLOWSTONES));
        ingredients.setOutputLists(VanillaTypes.ITEM,
                outputs[3]!=ItemStack.EMPTY?Arrays.asList(Collections.singletonList(outputs[0]), Collections.singletonList(outputs[1]), Collections.singletonList(outputs[2]), Collections.singletonList(outputs[3])):
                        outputs[2]!=ItemStack.EMPTY?
                        Arrays.asList(Collections.singletonList(outputs[0]), Collections.singletonList(outputs[1]), Collections.singletonList(outputs[2])):
                                outputs[1]!=ItemStack.EMPTY?Arrays.asList(Collections.singletonList(outputs[0]), Collections.singletonList(outputs[1])):
                                        Arrays.asList(Collections.singletonList(outputs[0]))
                );
    }

    public void drawInfo(Minecraft mc, int width, int height, double mouseX, double mouseY) {
        for (int i = 0; i < tooltips.length; i++)
            mc.fontRenderer.drawString(I18n.format(tooltips[i]), 0, 80, 0);
    }
}