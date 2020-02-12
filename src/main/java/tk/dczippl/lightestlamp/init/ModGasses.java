package tk.dczippl.lightestlamp.init;

import mekanism.api.MekanismAPI;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasAttributes;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import tk.dczippl.lightestlamp.Reference;

import java.awt.*;

public class ModGasses
{
    public static final DeferredRegister<Gas> GASES = new DeferredRegister<>(MekanismAPI.GAS_REGISTRY, Reference.MOD_ID);
    public static RegistryObject<Gas> BROMINE_VAPOUR = GASES.register("bromine_vapour",() -> new Gas(GasAttributes.builder().color(new Color(102,16,0).getRGB())));
}