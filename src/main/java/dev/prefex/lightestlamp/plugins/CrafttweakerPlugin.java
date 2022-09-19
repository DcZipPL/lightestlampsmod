package dev.prefex.lightestlamp.plugins;

import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IRecipeHandlerRegistrationHandler;
import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeRecipe;

@CraftTweakerPlugin(Util.MOD_ID)
public class CrafttweakerPlugin implements ICraftTweakerPlugin {
    @Override
    public void registerRecipeHandlers(IRecipeHandlerRegistrationHandler handler) {
        ICraftTweakerPlugin.super.registerRecipeHandlers(handler);
        handler.registerRecipeHandler(GasCentrifugeRecipe.class,new CtCentrifugable());
    }
}
