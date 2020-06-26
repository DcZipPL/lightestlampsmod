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
    @ObjectHolder(Reference.MOD_ID+":"+"chorus_fiber")
    public static final Item CHORUS_FIBER = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("chorus_fiber");
    @ObjectHolder(Reference.MOD_ID+":"+"moon_shard")
    public static final Item MOON_SHARD = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("moon_shard");
    @ObjectHolder(Reference.MOD_ID+":"+"krypton_small_dust")
    public static final Item KRYPTON_PILE = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("krypton_small_dust");
    @ObjectHolder(Reference.MOD_ID+":"+"radon_dust")
    public static final Item RADON_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("radon_dust");
    @ObjectHolder(Reference.MOD_ID+":"+"xenon_dust")
    public static final Item XENON_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("xenon_dust");
    @ObjectHolder(Reference.MOD_ID+":"+"krypton_dust")
    public static final Item KRYPTON_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("krypton_dust");
    @ObjectHolder(Reference.MOD_ID+":"+"argon_dust")
    public static final Item ARGON_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("argon_dust");
    @ObjectHolder(Reference.MOD_ID+":"+"neon_dust")
    public static final Item NEON_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("neon_dust");
    @ObjectHolder(Reference.MOD_ID+":"+"radon_pile")
    public static final Item RADON_PILE = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("radon_pile");
    @ObjectHolder(Reference.MOD_ID+":"+"xenon_pile")
    public static final Item XENON_PILE = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("xenon_pile");
    @ObjectHolder(Reference.MOD_ID+":"+"argon_pile")
    public static final Item ARGON_PILE = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("argon_pile");
    @ObjectHolder(Reference.MOD_ID+":"+"neon_pile")
    public static final Item NEON_PILE = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("neon_pile");
    @ObjectHolder(Reference.MOD_ID+":"+"empty_rod")
    public static final Item EMPTY_ROD = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("empty_rod");
    @ObjectHolder(Reference.MOD_ID+":"+"radon_rod")
    public static final Item RADON_ROD = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("radon_rod");
    @ObjectHolder(Reference.MOD_ID+":"+"xenon_rod")
    public static final Item XENON_ROD = new Item(new Item.Properties().maxStackSize(16).group(Main.main_group)).setRegistryName("xenon_rod");
    @ObjectHolder(Reference.MOD_ID+":"+"krypton_rod")
    public static final Item KRYPTON_ROD = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("krypton_rod");
    @ObjectHolder(Reference.MOD_ID+":"+"argon_rod")
    public static final Item ARGON_ROD = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("argon_rod");
    @ObjectHolder(Reference.MOD_ID+":"+"neon_rod")
    public static final Item NEON_ROD = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("neon_rod");
    @ObjectHolder(Reference.MOD_ID+":"+"carbon_nanotube")
    public static final Item CARBON_NANOTUBE = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("carbon_nanotube");
    @ObjectHolder(Reference.MOD_ID+":"+"bromine_crystal")
    public static final Item BROMINE_CRYSTAL = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("bromine_crystal");
    @ObjectHolder(Reference.MOD_ID+":"+"boron_mesh")
    public static final Item BORON_MESH = new Item(new Item.Properties().maxStackSize(16).group(Main.main_group)).setRegistryName("boron_mesh");
    @ObjectHolder(Reference.MOD_ID+":"+"boric_acid")
    public static final Item BORIC_ACID = new Item(new Item.Properties().maxStackSize(16).group(Main.main_group)).setRegistryName("boric_acid");
    @ObjectHolder(Reference.MOD_ID+":"+"alchemical_mesh")
    public static final Item ALCHEMICAL_MESH = new Item(new Item.Properties().maxStackSize(16).group(Main.main_group)).setRegistryName("alchemical_mesh");
    @ObjectHolder(Reference.MOD_ID+":"+"debug_stick")
    public static final Item DEBUG_STICK = new DebugStickItem(new Item.Properties().group(Main.main_group)).setRegistryName("debug_stick");
    @ObjectHolder(Reference.MOD_ID+":"+"alchemical_dust")
    public static final Item ALCHEMICAL_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("alchemical_dust");
    @ObjectHolder(Reference.MOD_ID+":"+"stickandbowl")
    public static final Item STICKANDBOWL = new StickAndBowlItem(new Item.Properties().maxStackSize(1).group(Main.main_group)).setRegistryName("stickandbowl");
    public static final Item BASIC_FILTER = new FilterItem(new Item.Properties().maxStackSize(1).maxDamage(124).group(Main.main_group),"tooltip.lightestlamp.basic_filter").setRegistryName("basic_centrifuge_filter");
    public static final Item NEON_FILTER = new FilterItem(new Item.Properties().maxStackSize(1).maxDamage(118).group(Main.main_group),"tooltip.lightestlamp.neon_filter").setRegistryName("neon_centrifuge_filter");
    public static final Item ARGON_FILTER = new FilterItem(new Item.Properties().maxStackSize(1).maxDamage(105).group(Main.main_group),"tooltip.lightestlamp.argon_filter").setRegistryName("argon_centrifuge_filter");
    public static final Item KRYPTON_FILTER = new FilterItem(new Item.Properties().maxStackSize(1).maxDamage(83).group(Main.main_group),"tooltip.lightestlamp.krypton_filter").setRegistryName("krypton_centrifuge_filter");
    public static final Item BROMINE_FILTER = new FilterItem(new Item.Properties().maxStackSize(1).maxDamage(31).group(Main.main_group),"tooltip.lightestlamp.bromine_filter").setRegistryName("bromine_centrifuge_filter");
    public static final Item XENON_FILTER = new FilterItem(new Item.Properties().maxStackSize(1).maxDamage(92).group(Main.main_group),"tooltip.lightestlamp.xenon_filter").setRegistryName("xenon_centrifuge_filter");
    public static final Item RADON_FILTER = new FilterItem(new Item.Properties().maxStackSize(1).maxDamage(62).group(Main.main_group),"tooltip.lightestlamp.radon_filter").setRegistryName("radon_centrifuge_filter");
    public static final Item BORON_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("boron_dust");
    public static final Item BORON_PILE = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("boron_pile");
    public static final Item BORON_INGOT = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("boron_ingot");
    public static final Item BORON_NUGGET = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("boron_nugget");

    public static final Item GLOWING_DUST_AGGLOMERATIO = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("glowing_dust_agglomeratio");
}