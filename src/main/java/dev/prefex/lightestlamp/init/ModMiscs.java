package dev.prefex.lightestlamp.init;

import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeMenu;
import net.minecraftforge.registries.RegistryObject;

public class ModMiscs
{
    public static void init(IEventBus modEventBus){
        ModMiscs.MENUS.register(modEventBus);
        ModMiscs.RECIPE_SERIALIZERS.register(modEventBus);
        ModMiscs.RECIPE_TYPES.register(modEventBus);
    }
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Util.MOD_ID);

    // --- MENUS --- //

    public static final RegistryObject<MenuType<GasCentrifugeMenu>> GAS_CENTRIFUGE = MENUS.register("gas_centrifuge_menu", () -> IForgeMenuType.create(GasCentrifugeMenu::new));

    // --- RECIPES --- //

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Util.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, Util.MOD_ID);

    public static final RegistryObject<RecipeType<GasCentrifugeRecipe>> GLOWSTONE_CENTRIFUGE_RECIPE_TYPE =
            RECIPE_TYPES.register("glowstone_centrifuge", () -> new RecipeType<>() {});

    public static final RegistryObject<RecipeSerializer<GasCentrifugeRecipe>> GLOWSTONE_CENTRIFUGE_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("glowstone_centrifuge", GasCentrifugeRecipe.Serializer::new);
}