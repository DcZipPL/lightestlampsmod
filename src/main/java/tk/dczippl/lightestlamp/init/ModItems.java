package tk.dczippl.lightestlamp.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.ObjectHolder;
import tk.dczippl.lightestlamp.Main;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.items.DebugStickItem;
import tk.dczippl.lightestlamp.items.WrittenBookItem;

@ObjectHolder(Reference.MOD_ID)
public class ModItems
{
    public static final Item KRYPTON_SMALL_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("krypton_small_dust");
    public static final Item KRYPTON_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("krypton_dust");
    public static final Item ARGON_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("argon_dust");
    public static final Item NEON_DUST = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("neon_dust");
    public static final Item EMPTY_ROD = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("empty_rod");
    public static final Item KRYPTON_ROD = new Item(new Item.Properties().maxStackSize(16).group(Main.main_group)).setRegistryName("krypton_rod");
    public static final Item ARGON_ROD = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("argon_rod");
    public static final Item NEON_ROD = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("neon_rod");
    public static final Item CARBON_NANOTUBE = new Item(new Item.Properties().maxStackSize(64).group(Main.main_group)).setRegistryName("carbon_nanotube");
    public static final Item DEBUG_STICK = new DebugStickItem(new Item.Properties().group(Main.main_group)).setRegistryName("debug_stick");
    public static final Item WRITTEN_BOOK = new WrittenBookItem(new Item.Properties().maxStackSize(1).group(Main.main_group)).setRegistryName("written_book");
    public static final Item CRIMSON_DYE = new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName("crimson_dye");
}