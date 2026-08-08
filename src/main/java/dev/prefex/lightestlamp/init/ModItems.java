package dev.prefex.lightestlamp.init;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.items.FilterItem;
import dev.prefex.lightestlamp.items.StickAndBowlItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItems
{
    public static void init(IEventBus modEventBus){
        ModBlocks.BLOCKS.getEntries().forEach(block ->
                ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties())));
        ModItems.ITEMS.register(modEventBus);
    }

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Util.MOD_ID);
    
    public static final DeferredItem<Item> CHORUS_FIBER = ITEMS.register("chorus_fiber",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> GLOW_LICHEN_FIBER = ITEMS.register("glow_lichen_fiber",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> RADON_DUST = ITEMS.register("radon_dust",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> XENON_DUST = ITEMS.register("xenon_dust",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> KRYPTON_DUST = ITEMS.register("krypton_dust",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> ARGON_DUST = ITEMS.register("argon_dust",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> NEON_DUST = ITEMS.register("neon_dust",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> RADON_PILE = ITEMS.register("radon_pile",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> XENON_PILE = ITEMS.register("xenon_pile",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> KRYPTON_PILE = ITEMS.register("krypton_pile",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> ARGON_PILE = ITEMS.register("argon_pile",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> NEON_PILE = ITEMS.register("neon_pile",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> GLASS_TUBE = ITEMS.register("glass_tube",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> RADON_TUBE = ITEMS.register("radon_tube",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> XENON_TUBE = ITEMS.register("xenon_tube",() -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> KRYPTON_TUBE = ITEMS.register("krypton_tube",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> ARGON_TUBE = ITEMS.register("argon_tube",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> NEON_TUBE = ITEMS.register("neon_tube",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> CARBON_NANOTUBE = ITEMS.register("carbon_nanotube",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> LANTHANUM_MESH = ITEMS.register("lanthanum_mesh",() -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> NETHERITE_MESH = ITEMS.register("netherite_mesh",() -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> ALCHEMICAL_DUST = ITEMS.register("alchemical_dust",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> STICKANDBOWL = ITEMS.register("stickandbowl",() -> new StickAndBowlItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BASIC_FILTER = ITEMS.register("basic_filter",() -> new FilterItem(new Item.Properties().stacksTo(1).durability(124),"tooltip.lightestlamp.basic_filter"));
    public static final DeferredItem<Item> NEON_FILTER = ITEMS.register("neon_filter",() -> new FilterItem(new Item.Properties().stacksTo(1).durability(118),"tooltip.lightestlamp.neon_filter"));
    public static final DeferredItem<Item> ARGON_FILTER = ITEMS.register("argon_filter",() -> new FilterItem(new Item.Properties().stacksTo(1).durability(105),"tooltip.lightestlamp.argon_filter"));
    public static final DeferredItem<Item> KRYPTON_FILTER = ITEMS.register("krypton_filter",() -> new FilterItem(new Item.Properties().stacksTo(1).durability(83),"tooltip.lightestlamp.krypton_filter"));
    public static final DeferredItem<Item> BROMINE_FILTER = ITEMS.register("bromine_filter",() -> new FilterItem(new Item.Properties().stacksTo(1).durability(31),"tooltip.lightestlamp.bromine_filter"));
    public static final DeferredItem<Item> XENON_FILTER = ITEMS.register("xenon_filter",() -> new FilterItem(new Item.Properties().stacksTo(1).durability(92),"tooltip.lightestlamp.xenon_filter"));
    public static final DeferredItem<Item> RADON_FILTER = ITEMS.register("radon_filter",() -> new FilterItem(new Item.Properties().stacksTo(1).durability(62),"tooltip.lightestlamp.radon_filter"));
    public static final DeferredItem<Item> LANTHANUM_DUST = ITEMS.register("lanthanum_dust",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> LANTHANUM_PILE = ITEMS.register("lanthanum_pile",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> LANTHANUM_INGOT = ITEMS.register("lanthanum_ingot",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> LANTHANUM_NUGGET = ITEMS.register("lanthanum_nugget",() -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> RAW_LANTHANUM = ITEMS.register("raw_lanthanum",() -> new Item(new Item.Properties().stacksTo(64)));
}
