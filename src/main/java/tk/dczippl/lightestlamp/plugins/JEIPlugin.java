package tk.dczippl.lightestlamp.plugins;

import com.google.common.collect.ImmutableSet;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.init.ModBlocks;
import tk.dczippl.lightestlamp.init.ModItems;

import java.util.Collections;

@JeiPlugin
public class JEIPlugin implements IModPlugin
{
    public static final String GAS_CENTRIFUGE = "gas_centrifuge";

    @Override
    public void registerRecipes(IRecipeRegistration reg)
    {
        //Formatter::off
        reg.addRecipes(ImmutableSet.of(
                new GasCentrifugeRecipeJEI(new ItemStack(ModItems.BASIC_FILTER), "jei.lightestlamp.bromine5"),
                new GasCentrifugeRecipeJEI(new ItemStack(ModItems.NEON_FILTER), "jei.lightestlamp.bromine10"),
                new GasCentrifugeRecipeJEI(new ItemStack(ModItems.ARGON_FILTER), "jei.lightestlamp.bromine2"),
                new GasCentrifugeRecipeJEI(new ItemStack(ModItems.KRYPTON_FILTER), "jei.lightestlamp.bromine15"),
                new GasCentrifugeRecipeJEI(new ItemStack(ModItems.XENON_FILTER), "jei.lightestlamp.bromine2"),
                new GasCentrifugeRecipeJEI(new ItemStack(ModItems.RADON_FILTER), "jei.lightestlamp.bromine50"),
                new GasCentrifugeRecipeJEI(new ItemStack(ModItems.BROMINE_FILTER), "jei.lightestlamp.bromine75")
        ), getPluginUid());
        //Formatter::on
        reg.addIngredientInfo(new ItemStack(ModItems.MOON_SHARD), VanillaTypes.ITEM, "jei.lightestlamp.instructions.moonshard");
        reg.addIngredientInfo(new ItemStack(ModItems.NETHERITE_MESH), VanillaTypes.ITEM, "jei.lightestlamp.instructions.mesh");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration reg) {
        reg.addRecipeCatalyst(new ItemStack(ModBlocks.GAS_EXTRACTOR.get()), getPluginUid());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration reg) {
        reg.addRecipeCategories(new GasCentrifugeRecipeCategory(reg.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Reference.MOD_ID, GAS_CENTRIFUGE);
    }
}
