package dev.prefex.lightestlamp.init;

import dev.prefex.lightestlamp.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import dev.prefex.lightestlamp.Reference;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeMenu;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus
{
    public static void init(IEventBus modEventBus){
        ModMenus.MENUS.register(modEventBus);
    }
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Reference.MOD_ID);

    public static RegistryObject<MenuType<GasCentrifugeMenu>> GAS_CENTRIFUGE = MENUS.register("gas_centrifuge_menu", () -> IForgeMenuType.create((windowId, inv, data) -> {
        Main.LOGGER.warn("ModMenus: pos: ");
        return new GasCentrifugeMenu(windowId,inv,data);
    }));
}