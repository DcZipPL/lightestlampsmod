package dev.prefex.lightestlamp;

import dev.prefex.lightestlamp.init.ModBlockEntities;
import dev.prefex.lightestlamp.init.ModBlocks;
import dev.prefex.lightestlamp.init.ModMiscs;
import dev.prefex.lightestlamp.init.ModItems;
import dev.prefex.lightestlamp.lighting.LampLightEvents;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeBlockEntity;
import net.neoforged.fml.ModList;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import dev.prefex.lightestlamp.util.network.Networking;

import static dev.prefex.lightestlamp.Util.MOD_ID;

@Mod(MOD_ID)
public class Main
{
    public Main(IEventBus modEventBus, ModContainer modContainer)
    {
        ModBlocks.init(modEventBus);
        ModBlockEntities.init(modEventBus);
        ModMiscs.init(modEventBus);
        ModItems.init(modEventBus);
        if (ModList.get().isLoaded("mekanism")) {
            dev.prefex.lightestlamp.compat.mekanism.MekanismCompat.init(modEventBus);
        }
        LampLightEvents.init();
        NeoForge.EVENT_BUS.addListener(Main::tagsUpdated);

        modEventBus.addListener(Networking::register);
        modEventBus.addListener(Main::registerCapabilities);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.CENTRIFUGE_BE.get(),
                (blockEntity, side) -> blockEntity.getItemHandler(side));
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.CENTRIFUGE_BE.get(),
                (blockEntity, side) -> blockEntity.getEnergyStorage());
    }

    private static void tagsUpdated(net.neoforged.neoforge.event.TagsUpdatedEvent event) {
        if (event.shouldUpdateStaticData()) {
            GasCentrifugeBlockEntity.invalidateBurnTimes();
        }
    }
}
