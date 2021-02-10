package tk.dczippl.lightestlamp.plugins;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        GLOWSTONES.add(new ItemStack(Blocks.SHROOMLIGHT));

        //Mekanism compatibility
        ITag<Item> refined_glowstones = ItemTags.getCollection().get(new ResourceLocation("forge:ingots/refined_glowstone"));
        if (refined_glowstones!=null)
            ((Tag)refined_glowstones).getAllElements().forEach(item -> {
                GLOWSTONES.add(new ItemStack((Item)item));
            });
        ITag<Item> refined_glowstones_block = ItemTags.getCollection().get(new ResourceLocation("forge:storage_blocks/refined_glowstone"));
        if (refined_glowstones_block!=null)
            ((Tag)refined_glowstones_block).getAllElements().forEach(item -> {
                GLOWSTONES.add(new ItemStack((Item)item));
            });
        ITag<Item> refined_glowstones_nugget = ItemTags.getCollection().get(new ResourceLocation("forge:nuggets/refined_glowstone"));
        if (refined_glowstones_nugget!=null)
            ((Tag)refined_glowstones_nugget).getAllElements().forEach(item -> {
                GLOWSTONES.add(new ItemStack((Item)item));
            });
        ITag<Item> glowstone_blocks = ItemTags.getCollection().get(new ResourceLocation("forge:storage_blocks/glowstone"));
        if (glowstone_blocks!=null)
            ((Tag)glowstone_blocks).getAllElements().forEach(item -> {
                GLOWSTONES.add(new ItemStack((Item)item));
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

    public void drawInfo(Minecraft mc, MatrixStack matrixStack, int width, int height, double mouseX, double mouseY) {
        for (int i = 0; i < tooltips.length; i++)
            mc.fontRenderer.func_238422_b_(matrixStack, IReorderingProcessor.fromString(new StringTextComponent(I18n.format(tooltips[i])).getString(), Style.EMPTY), 0, 80, 0);
    }
}