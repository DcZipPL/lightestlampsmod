package dev.prefex.lightestlamp.plugins;

import dev.prefex.lightestlamp.Util;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIPlugin implements IModPlugin
{
    public static final String GAS_CENTRIFUGE = "gas_centrifuge";

    @Override
    public void registerRecipes(IRecipeRegistration reg)
    {/*
        //Formatter::off
        reg.addRecipes(ImmutableSet.of(
                new GasCentrifugeRecipeJEI(new ItemStack(ModItems.BASIC_FILTER.get()), "jei.lightestlamp.bromine5"),
                new GasCentrifugeRecipeJEI(new ItemStack(ModItems.NEON_FILTER.get()), "jei.lightestlamp.bromine10"),
                new GasCentrifugeRecipeJEI(new ItemStack(ModItems.ARGON_FILTER.get()), "jei.lightestlamp.bromine2"),
                new GasCentrifugeRecipeJEI(new ItemStack(ModItems.KRYPTON_FILTER.get()), "jei.lightestlamp.bromine15"),
                new GasCentrifugeRecipeJEI(new ItemStack(ModItems.XENON_FILTER.get()), "jei.lightestlamp.bromine2"),
                new GasCentrifugeRecipeJEI(new ItemStack(ModItems.RADON_FILTER.get()), "jei.lightestlamp.bromine50"),
                new GasCentrifugeRecipeJEI(new ItemStack(ModItems.BROMINE_FILTER.get()), "jei.lightestlamp.bromine75")
        ), getPluginUid());
        //Formatter::on
        reg.addIngredientInfo(new ItemStack(ModItems.NETHERITE_MESH.get()), VanillaTypes.ITEM, "jei.lightestlamp.instructions.mesh");
    */}

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration reg) {
        //reg.addRecipeCatalyst(new ItemStack(ModBlocks.GAS_EXTRACTOR.get()), getPluginUid());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration reg) {
        //reg.addRecipeCategories(new GasCentrifugeRecipeCategory(reg.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Util.MOD_ID, GAS_CENTRIFUGE);
    }
}
