package dev.prefex.lightestlamp.init;

import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeMenu;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Collectors;

import static dev.prefex.lightestlamp.Util.MOD_ID;

public class ModMiscs
{
    public static void init(IEventBus modEventBus){
        ModMiscs.MENUS.register(modEventBus);
        ModMiscs.RECIPE_SERIALIZERS.register(modEventBus);
        ModMiscs.RECIPE_TYPES.register(modEventBus);
        ModMiscs.CREATIVE_TABS.register(modEventBus);
    }
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);

    // --- MENUS --- //

    public static final RegistryObject<MenuType<GasCentrifugeMenu>> GAS_CENTRIFUGE = MENUS.register("gas_centrifuge_menu", () -> IForgeMenuType.create(GasCentrifugeMenu::new));

    // --- RECIPES --- //

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MOD_ID);

    public static final RegistryObject<RecipeType<GasCentrifugeRecipe>> GLOWSTONE_CENTRIFUGE_RECIPE_TYPE =
            RECIPE_TYPES.register("glowstone_centrifuge", () -> new RecipeType<>() {});

    public static final RegistryObject<RecipeSerializer<GasCentrifugeRecipe>> GLOWSTONE_CENTRIFUGE_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("glowstone_centrifuge", GasCentrifugeRecipe.Serializer::new);

    // --- CREATIVE TABS --- //

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<CreativeModeTab> LAMPS_TAB = CREATIVE_TABS.register("lamps", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group." + MOD_ID + ".llamps"))
            .icon(() -> new ItemStack(ModBlocks.OMEGA_LAMP.get(), 16))
            .displayItems((params, output) -> {
                output.acceptAll(ModBlocks.BLOCKS.getEntries()
                        .stream()
                        .filter((pred) -> pred.get() != ModBlocks.LIGHT_AIR.get() && pred.get() != ModBlocks.WATERLOGGABLE_LIGHT_AIR.get())
                        .map((entry) -> new ItemStack(entry.get()))
                        .collect(Collectors.toList())
                );
                output.acceptAll(ModItems.ITEMS.getEntries()
                        .stream()
                        .map((entry) -> new ItemStack(entry.get()))
                        .collect(Collectors.toList())
                );
            })
            .build()
    );
}