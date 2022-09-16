package dev.prefex.lightestlamp.init;

import dev.prefex.lightestlamp.Main;
import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.items.FilterItem;
import dev.prefex.lightestlamp.items.StickAndBowlItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    public static void init(IEventBus modEventBus){
        ModItems.ITEMS.register(modEventBus);
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Util.MOD_ID);
    
    public static final RegistryObject<Item> CHORUS_FIBER = ITEMS.register("chorus_fiber",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> GLOW_LICHEN_FIBER = ITEMS.register("glow_lichen_fiber",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> RADON_DUST = ITEMS.register("radon_dust",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> XENON_DUST = ITEMS.register("xenon_dust",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> KRYPTON_DUST = ITEMS.register("krypton_dust",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> ARGON_DUST = ITEMS.register("argon_dust",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> NEON_DUST = ITEMS.register("neon_dust",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> RADON_PILE = ITEMS.register("radon_pile",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> XENON_PILE = ITEMS.register("xenon_pile",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> KRYPTON_PILE = ITEMS.register("krypton_pile",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> ARGON_PILE = ITEMS.register("argon_pile",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> NEON_PILE = ITEMS.register("neon_pile",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> GLASS_TUBE = ITEMS.register("glass_tube",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> RADON_TUBE = ITEMS.register("radon_tube",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> XENON_TUBE = ITEMS.register("xenon_tube",() -> new Item(new Item.Properties().stacksTo(16).tab(Main.main_tab)));
    public static final RegistryObject<Item> KRYPTON_TUBE = ITEMS.register("krypton_tube",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> ARGON_TUBE = ITEMS.register("argon_tube",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> NEON_TUBE = ITEMS.register("neon_tube",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> CARBON_NANOTUBE = ITEMS.register("carbon_nanotube",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> LANTHANUM_MESH = ITEMS.register("lanthanum_mesh",() -> new Item(new Item.Properties().stacksTo(16).tab(Main.main_tab)));
    public static final RegistryObject<Item> NETHERITE_MESH = ITEMS.register("netherite_mesh",() -> new Item(new Item.Properties().stacksTo(16).tab(Main.main_tab)));
    public static final RegistryObject<Item> ALCHEMICAL_DUST = ITEMS.register("alchemical_dust",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> STICKANDBOWL = ITEMS.register("stickandbowl",() -> new StickAndBowlItem(new Item.Properties().stacksTo(1).tab(Main.main_tab)));
    public static final RegistryObject<Item> BASIC_FILTER = ITEMS.register("basic_filter",() -> new FilterItem(new Item.Properties().stacksTo(1).durability(124).tab(Main.main_tab),"tooltip.lightestlamp.basic_filter"));
    public static final RegistryObject<Item> NEON_FILTER = ITEMS.register("neon_filter",() -> new FilterItem(new Item.Properties().stacksTo(1).durability(118).tab(Main.main_tab),"tooltip.lightestlamp.neon_filter"));
    public static final RegistryObject<Item> ARGON_FILTER = ITEMS.register("argon_filter",() -> new FilterItem(new Item.Properties().stacksTo(1).durability(105).tab(Main.main_tab),"tooltip.lightestlamp.argon_filter"));
    public static final RegistryObject<Item> KRYPTON_FILTER = ITEMS.register("krypton_filter",() -> new FilterItem(new Item.Properties().stacksTo(1).durability(83).tab(Main.main_tab),"tooltip.lightestlamp.krypton_filter"));
    public static final RegistryObject<Item> BROMINE_FILTER = ITEMS.register("bromine_filter",() -> new FilterItem(new Item.Properties().stacksTo(1).durability(31).tab(Main.main_tab),"tooltip.lightestlamp.bromine_filter"));
    public static final RegistryObject<Item> XENON_FILTER = ITEMS.register("xenon_filter",() -> new FilterItem(new Item.Properties().stacksTo(1).durability(92).tab(Main.main_tab),"tooltip.lightestlamp.xenon_filter"));
    public static final RegistryObject<Item> RADON_FILTER = ITEMS.register("radon_filter",() -> new FilterItem(new Item.Properties().stacksTo(1).durability(62).tab(Main.main_tab),"tooltip.lightestlamp.radon_filter"));
    public static final RegistryObject<Item> LANTHANUM_DUST = ITEMS.register("lanthanum_dust",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> LANTHANUM_PILE = ITEMS.register("lanthanum_pile",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> LANTHANUM_INGOT = ITEMS.register("lanthanum_ingot",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> LANTHANUM_NUGGET = ITEMS.register("lanthanum_nugget",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
    public static final RegistryObject<Item> RAW_LANTHANUM = ITEMS.register("raw_lanthanum",() -> new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)));
}