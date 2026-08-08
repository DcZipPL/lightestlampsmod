package dev.prefex.lightestlamp.init;

import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeMenu;

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
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MOD_ID);

    // --- MENUS --- //

    public static final DeferredHolder<MenuType<?>, MenuType<GasCentrifugeMenu>> GAS_CENTRIFUGE = MENUS.register("gas_centrifuge_menu", () -> IMenuTypeExtension.create(GasCentrifugeMenu::new));

    // --- RECIPES --- //

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<GasCentrifugeRecipe>> GLOWSTONE_CENTRIFUGE_RECIPE_TYPE =
            RECIPE_TYPES.register("glowstone_centrifuge", () -> new RecipeType<>() {});

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<GasCentrifugeRecipe>> GLOWSTONE_CENTRIFUGE_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("glowstone_centrifuge", GasCentrifugeRecipe.Serializer::new);

    // --- CREATIVE TABS --- //

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> LAMPS_TAB = CREATIVE_TABS.register("lamps", () -> CreativeModeTab.builder()
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
                        .filter(entry -> !(entry.get() instanceof BlockItem))
                        .map((entry) -> new ItemStack(entry.get()))
                        .collect(Collectors.toList())
                );
            })
            .build()
    );
}
