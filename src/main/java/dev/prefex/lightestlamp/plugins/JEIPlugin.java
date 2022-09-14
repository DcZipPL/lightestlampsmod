package dev.prefex.lightestlamp.plugins;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.init.ModBlocks;
import dev.prefex.lightestlamp.init.ModItems;
import dev.prefex.lightestlamp.init.ModMiscs;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin
{
    public static final String GAS_CENTRIFUGE = "gas_centrifuge";

    public static final RecipeType<GasCentrifugeRecipe> JEI_RECIPE = RecipeType.create(
            ModMiscs.GLOWSTONE_CENTRIFUGE_RECIPE_TYPE.getId().getNamespace(),
            ModMiscs.GLOWSTONE_CENTRIFUGE_RECIPE_TYPE.getId().getPath(),
            GasCentrifugeRecipe.class
    );

    @Override
    public void registerRecipes(IRecipeRegistration reg)
    {
        List<GasCentrifugeRecipe> recipes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModMiscs.GLOWSTONE_CENTRIFUGE_RECIPE_TYPE.get());

        reg.addRecipes(
                JEI_RECIPE, recipes
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration reg) {
        reg.addRecipeCatalyst(VanillaTypes.ITEM, new ItemStack(ModBlocks.GLOWSTONE_CENTRIFUGE.get()), JEI_RECIPE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration reg) {
        reg.addRecipeCategories(new GasCentrifugeRecipeCategory(reg.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Util.MOD_ID, GAS_CENTRIFUGE);
    }
}
