package dev.prefex.lightestlamp;

import dev.prefex.lightestlamp.init.ModMiscs;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = Util.MOD_ID, value = Dist.CLIENT)
public final class ClientEvents {
    private ClientEvents() {
    }

    @SubscribeEvent
    static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMiscs.GAS_CENTRIFUGE.get(), GasCentrifugeScreen::new);
    }
}
