package tk.dczippl.lightestlamp.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ObjectHolder;
import tk.dczippl.lightestlamp.Main;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.items.*;

public class ModItems
{
    public static final Item CHORUS_FIBER = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("chorus_fiber");
    public static final Item MOON_SHARD = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("moon_shard");
    public static final Item KRYPTON_PILE = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("krypton_small_dust");
    public static final Item RADON_DUST = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("radon_dust");
    public static final Item XENON_DUST = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("xenon_dust");
    public static final Item KRYPTON_DUST = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("krypton_dust");
    public static final Item ARGON_DUST = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("argon_dust");
    public static final Item NEON_DUST = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("neon_dust");
    public static final Item RADON_PILE = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("radon_pile");
    public static final Item XENON_PILE = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("xenon_pile");
    public static final Item ARGON_PILE = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("argon_pile");
    public static final Item NEON_PILE = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("neon_pile");
    public static final Item EMPTY_ROD = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("empty_tube");
    public static final Item RADON_ROD = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("radon_tube");
    public static final Item XENON_ROD = new Item(new Item.Properties().stacksTo(16).tab(Main.main_tab)).setRegistryName("xenon_tube");
    public static final Item KRYPTON_ROD = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("krypton_tube");
    public static final Item ARGON_ROD = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("argon_tube");
    public static final Item NEON_ROD = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("neon_tube");
    public static final Item CARBON_NANOTUBE = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("carbon_nanotube");
    public static final Item BROMINE_CRYSTAL = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("bromine_crystal");
    public static final Item BORON_MESH = new Item(new Item.Properties().stacksTo(16).tab(Main.main_tab)).setRegistryName("lanthanum_mesh");
    public static final Item NETHERITE_MESH = new Item(new Item.Properties().stacksTo(16).tab(Main.main_tab)).setRegistryName("netherite_mesh");
    public static final Item DEBUG_STICK = new DebugStickItem(new Item.Properties().tab(Main.main_tab)).setRegistryName("debug_stick");
    public static final Item ALCHEMICAL_DUST = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("alchemical_dust");
    public static final Item STICKANDBOWL = new StickAndBowlItem(new Item.Properties().stacksTo(1).tab(Main.main_tab)).setRegistryName("stickandbowl");
    public static final Item BASIC_FILTER = new FilterItem(new Item.Properties().stacksTo(1).durability(124).tab(Main.main_tab),"tooltip.lightestlamp.basic_filter").setRegistryName("basic_centrifuge_filter");
    public static final Item NEON_FILTER = new FilterItem(new Item.Properties().stacksTo(1).durability(118).tab(Main.main_tab),"tooltip.lightestlamp.neon_filter").setRegistryName("neon_centrifuge_filter");
    public static final Item ARGON_FILTER = new FilterItem(new Item.Properties().stacksTo(1).durability(105).tab(Main.main_tab),"tooltip.lightestlamp.argon_filter").setRegistryName("argon_centrifuge_filter");
    public static final Item KRYPTON_FILTER = new FilterItem(new Item.Properties().stacksTo(1).durability(83).tab(Main.main_tab),"tooltip.lightestlamp.krypton_filter").setRegistryName("krypton_centrifuge_filter");
    public static final Item BROMINE_FILTER = new FilterItem(new Item.Properties().stacksTo(1).durability(31).tab(Main.main_tab),"tooltip.lightestlamp.bromine_filter").setRegistryName("bromine_centrifuge_filter");
    public static final Item XENON_FILTER = new FilterItem(new Item.Properties().stacksTo(1).durability(92).tab(Main.main_tab),"tooltip.lightestlamp.xenon_filter").setRegistryName("xenon_centrifuge_filter");
    public static final Item RADON_FILTER = new FilterItem(new Item.Properties().stacksTo(1).durability(62).tab(Main.main_tab),"tooltip.lightestlamp.radon_filter").setRegistryName("radon_centrifuge_filter");
    public static final Item LANTHANUM_DUST = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("lanthanum_dust");
    public static final Item LANTHANUM_PILE = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("lanthanum_pile");
    public static final Item LANTHANUM_INGOT = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("lanthanum_ingot");
    public static final Item LANTHANUM_NUGGET = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("lanthanum_nugget");
    public static final Item GLOWING_DUST_AGGLOMERATIO = new Item(new Item.Properties().stacksTo(64).tab(Main.main_tab)).setRegistryName("glowing_dust_agglomeratio");
}