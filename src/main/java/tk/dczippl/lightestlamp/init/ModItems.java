package tk.dczippl.lightestlamp.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.ObjectHolder;
import tk.dczippl.lightestlamp.Main;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.items.*;

@ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    public static final Item MOON_SHARD = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("moon_shard");
    public static final Item KRYPTON_SMALL_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("krypton_small_dust");
    public static final Item KRYPTON_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("krypton_dust");
    public static final Item ARGON_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("argon_dust");
    public static final Item NEON_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("neon_dust");
    public static final Item ARGON_PILE = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("argon_pile");
    public static final Item NEON_PILE = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("neon_pile");
    public static final Item EMPTY_ROD = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("empty_rod");
    public static final Item KRYPTON_ROD = new Item(new Item.Properties().maxStackSize(16).group(Main.main_group)).setRegistryName("krypton_rod");
    public static final Item ARGON_ROD = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("argon_rod");
    public static final Item NEON_ROD = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("neon_rod");
    public static final Item CARBON_NANOTUBE = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("carbon_nanotube");
    public static final Item DEBUG_STICK = new DebugStickItem(new Item.Properties().group(Main.main_group)).setRegistryName("debug_stick");
    public static final Item ALCHEMICAL_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("alchemical_dust");
    public static final Item STICKANDBOWL = new StickAndBowlItem(new Item.Properties().maxStackSize(1).group(Main.main_group)).setRegistryName("stickandbowl");
    public static final Item BASIC_FILTER = new FilterItem(new Item.Properties().maxStackSize(1).maxDamage(124).group(Main.main_group),"tooltip.lightestlamp.basic_filter").setRegistryName("basic_centrifuge_filter");
    public static final Item NEON_FILTER = new FilterItem(new Item.Properties().maxStackSize(1).maxDamage(118).group(Main.main_group),"tooltip.lightestlamp.neon_filter").setRegistryName("neon_centrifuge_filter");
    public static final Item ARGON_FILTER = new FilterItem(new Item.Properties().maxStackSize(1).maxDamage(105).group(Main.main_group),"tooltip.lightestlamp.argon_filter").setRegistryName("argon_centrifuge_filter");
    public static final Item KRYPTON_FILTER = new FilterItem(new Item.Properties().maxStackSize(1).maxDamage(83).group(Main.main_group),"tooltip.lightestlamp.krypton_filter").setRegistryName("krypton_centrifuge_filter");
    public static final Item BROMINE_FILTER = new FilterItem(new Item.Properties().maxStackSize(1).maxDamage(31).group(Main.main_group),"tooltip.lightestlamp.bromine_filter").setRegistryName("bromine_centrifuge_filter");
    public static final Item BORON_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("boron_dust");
    public static final Item BORON_INGOT = new ControlledItem(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("boron_ingot");
    public static final Item BORON_NUGGET = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("boron_nugget");
    public static final Item WRITTEN_BOOK = new WrittenBookItem(new Item.Properties().maxStackSize(1).group(Main.main_group)).setRegistryName("written_book");
}