package tk.dczippl.lightestlamp.plugins;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.init.ModItems;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeRecipe;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

//@JeiPlugin
public class JEIPlugin //implements IModPlugin
{
    /*public static final String FASTFURNACE_ID = "MyMod.FastFurnace";
    public static final String FLOADER_ID = "MyMod.Floader";

    @Override
    public void register(@Nonnull IModRegistry registry) {
        registerFastFurnaceHandling(registry);
        registerFloaderHandling(registry);
    }

    private void registerFloaderHandling(@Nonnull IModRegistry registry) {
        IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.blockFloader), FLOADER_ID);
        registry.addRecipes(Collections.singletonList(new FloadRecipe()), FLOADER_ID);
        registry.handleRecipes(FloadRecipe.class, recipe -> new FloadRecipeWrapper(), FLOADER_ID);

        transferRegistry.addRecipeTransferHandler(ContainerFloader.class, FLOADER_ID,
                0, TileFloader.INPUT_SLOTS, TileFloader.INPUT_SLOTS, 36);
    }

    private void registerFastFurnaceHandling(@Nonnull IModRegistry registry) {
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.blockFastFurnace), FASTFURNACE_ID, VanillaRecipeCategoryUid.SMELTING);

        registry.addRecipes(CustomRecipeRegistry.getCustomRecipeList(), FASTFURNACE_ID);
        registry.handleRecipes(CustomRecipe.class, CustomRecipeWrapper::new, FASTFURNACE_ID);

        IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();
        transferRegistry.addRecipeTransferHandler(ContainerFastFurnace.class, VanillaRecipeCategoryUid.SMELTING,
                0, TileFastFurnace.INPUT_SLOTS, TileFastFurnace.INPUT_SLOTS + TileFastFurnace.OUTPUT_SLOTS, 36);
        transferRegistry.addRecipeTransferHandler(ContainerFastFurnace.class, FASTFURNACE_ID,
                0, TileFastFurnace.INPUT_SLOTS, TileFastFurnace.INPUT_SLOTS + TileFastFurnace.OUTPUT_SLOTS, 36);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers helpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = helpers.getGuiHelper();

        registry.addRecipeCategories(new FloadRecipeCategory(guiHelper));
        registry.addRecipeCategories(new CustomRecipeCategory(guiHelper));
    }*/
}
