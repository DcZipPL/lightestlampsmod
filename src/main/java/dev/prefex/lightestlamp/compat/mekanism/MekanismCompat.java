package dev.prefex.lightestlamp.compat.mekanism;

import dev.prefex.lightestlamp.Util;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalBuilder;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class MekanismCompat {
    private static final int LANTHANUM_TINT = 0x78DDE3;

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Util.MOD_ID);
    public static final DeferredItem<Item> CRYSTAL_LANTHANUM = item("crystal_lanthanum");
    public static final DeferredItem<Item> SHARD_LANTHANUM = item("shard_lanthanum");
    public static final DeferredItem<Item> CLUMP_LANTHANUM = item("clump_lanthanum");
    public static final DeferredItem<Item> DIRTY_DUST_LANTHANUM = item("dirty_dust_lanthanum");

    public static final DeferredRegister<Chemical> CHEMICALS = DeferredRegister.create(MekanismAPI.CHEMICAL_REGISTRY_NAME, Util.MOD_ID);
    public static final DeferredHolder<Chemical, Chemical> DIRTY_LANTHANUM = CHEMICALS.register(
            "dirty_lanthanum", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(LANTHANUM_TINT)));
    public static final DeferredHolder<Chemical, Chemical> CLEAN_LANTHANUM = CHEMICALS.register(
            "clean_lanthanum", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(LANTHANUM_TINT)));

    private MekanismCompat() {}

    public static void init(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        CHEMICALS.register(modEventBus);
    }

    public static void addCreativeItems(CreativeModeTab.Output output) {
        ITEMS.getEntries().forEach(item -> output.accept(item.get()));
    }

    private static DeferredItem<Item> item(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }
}
