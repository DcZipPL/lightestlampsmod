package dev.prefex.lightestlamp.machine.gascentrifuge;

import com.google.common.collect.Lists;
import dev.prefex.lightestlamp.init.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class GasCentrifugeLegacyRecipe
{
    public static final List<?> argon = Lists.newArrayList(
            new ItemStack(ModItems.ARGON_FILTER.get()), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.ARGON_DUST.get(),2),new ItemStack(ModItems.NEON_PILE.get(),2),new ItemStack(ModItems.XENON_PILE.get(),1),ItemStack.EMPTY);
    public static final List<?> krypton = Lists.newArrayList(
            new ItemStack(ModItems.KRYPTON_FILTER.get()), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.ARGON_PILE.get(),3),new ItemStack(ModItems.KRYPTON_DUST.get()),new ItemStack(ModItems.KRYPTON_PILE.get(),2),new ItemStack(ModItems.XENON_PILE.get(),2));
    public static final List<?> xenon = Lists.newArrayList(
            new ItemStack(ModItems.XENON_FILTER.get()), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.KRYPTON_PILE.get(),2),new ItemStack(ModItems.XENON_DUST.get()),new ItemStack(ModItems.XENON_PILE.get(),2),new ItemStack(ModItems.RADON_PILE.get(),1));
    public static final List<?> radon = Lists.newArrayList(
            new ItemStack(ModItems.RADON_FILTER.get()), new ItemStack(Items.GLOWSTONE),new ItemStack(ModItems.XENON_PILE.get(),2),new ItemStack(ModItems.RADON_PILE.get(),3),ItemStack.EMPTY,ItemStack.EMPTY);
    public static final List<?> bromine = Lists.newArrayList(
            new ItemStack(ModItems.BROMINE_FILTER.get()), new ItemStack(Items.GLOWSTONE),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY);
}