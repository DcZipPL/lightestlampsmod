package tk.dczippl.lightestlamp.init;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.registries.ObjectHolder;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.blocks.*;

@ObjectHolder(Reference.MOD_ID)
public class ModBlocks
{
    @ObjectHolder(Reference.MOD_ID+":"+"dark_air")
    public static final Block DARK_AIR = new DarkAirBlock().setRegistryName("dark_air");

    @ObjectHolder(Reference.MOD_ID+":"+"light_air")
    public static final Block LIGHT_AIR = new LightAirBlock().setRegistryName("light_air");

    @ObjectHolder(Reference.MOD_ID+":"+"clear_lamp")
    public static final Block CLEAR_LAMP = new ClearLampBlock(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.3f,1).lightValue(15)).setRegistryName("clear_lamp");

    @ObjectHolder(Reference.MOD_ID+":"+"alfa_lamp")
    public static final Block ALFA_LAMP = new AlfaLampBlock().setRegistryName("alfa_lamp");

    @ObjectHolder(Reference.MOD_ID+":"+"beta_lamp")
    public static final Block BETA_LAMP = new BetaLampBlock().setRegistryName("beta_lamp");

    @ObjectHolder(Reference.MOD_ID+":"+"gamma_lamp")
    public static final Block GAMMA_LAMP = new GammaLampBlock().setRegistryName("gamma_lamp");

    @ObjectHolder(Reference.MOD_ID+":"+"delta_lamp")
    public static final Block DELTA_LAMP = new DeltaLampBlock().setRegistryName("delta_lamp");

    @ObjectHolder(Reference.MOD_ID+":"+"epsilon_lamp")
    public static final Block EPSILON_LAMP = new EpsilonLampBlock().setRegistryName("epsilon_lamp");

    @ObjectHolder(Reference.MOD_ID+":"+"zeta_lamp")
    public static final Block ZETA_LAMP = new ZetaLampBlock().setRegistryName("zeta_lamp");

    @ObjectHolder(Reference.MOD_ID+":"+"omega_lamp")
    public static final Block OMEGA_LAMP = new OmegaLampBlock().setRegistryName("omega_lamp");

    @ObjectHolder(Reference.MOD_ID+":"+"debug_chunk_cleaner")
    public static final Block CHUNK_CLEANER = new ChunkCleanerBlock(Block.Properties.create(Material.WOOL)).setRegistryName("debug_chunk_cleaner");

    //Other lamps

    @ObjectHolder(Reference.MOD_ID+":"+"anti_lamp")
    public static final Block ANTI_LAMP = new AntiLampBlock().setRegistryName("anti_lamp");

    @ObjectHolder(Reference.MOD_ID+":"+"jungle_lantern")
    public static final Block JUNGLE_LANTERN = new JungleLanternBlock(Block.Properties.create(Material.LEAVES).sound(SoundType.PLANT).hardnessAndResistance(0.2f,1).lightValue(12)).setRegistryName("jungle_lantern");

    //Other Blocks

    @ObjectHolder(Reference.MOD_ID+":"+"neon_rod_block")
    public static final Block NEON_ROD_BLOCK = new Block(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.85f,1).lightValue(10)).setRegistryName("neon_rod_block");

    @ObjectHolder(Reference.MOD_ID+":"+"argon_rod_block")
    public static final Block ARGON_ROD_BLOCK = new Block(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS).hardnessAndResistance(0.85f,1).lightValue(10)).setRegistryName("argon_rod_block");

    @ObjectHolder(Reference.MOD_ID+":"+"vanta_black")
    public static final Block VANTA_BLACK = new Block(Block.Properties.create(Material.ROCK).sound(SoundType.CLOTH).hardnessAndResistance(0.1f,1)).setRegistryName("vanta_black");

    //Glowstones

    @ObjectHolder(Reference.MOD_ID+":"+"neon_block")
    public static final Block NEON_BLOCK = new Block(Block.Properties.create(Material.REDSTONE_LIGHT).sound(SoundType.GLASS).hardnessAndResistance(0.4f,1).lightValue(15)).setRegistryName("neon_block");

    @ObjectHolder(Reference.MOD_ID+":"+"argon_block")
    public static final Block ARGON_BLOCK = new Block(Block.Properties.create(Material.REDSTONE_LIGHT).sound(SoundType.GLASS).hardnessAndResistance(0.4f,1).lightValue(15)).setRegistryName("argon_block");

    @ObjectHolder(Reference.MOD_ID+":"+"krypton_block")
    public static final Block KRYPTON_BLOCK = new Block(Block.Properties.create(Material.REDSTONE_LIGHT).sound(SoundType.GLASS).hardnessAndResistance(0.4f,1).lightValue(15)).setRegistryName("krypton_block");

    //public static final Block GAS_EXTRACTOR = new GasExtractorBlock(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(3,1)).setRegistryName("gas_extractor");

    public static final Block BIG_FLOWER = new BigFlowerBlock(Block.Properties.create(Material.PLANTS, MaterialColor.PINK).sound(SoundType.PLANT)).setRegistryName("big_flower");
    public static final Block CRIMSON_WOOL = new Block(Block.Properties.create(Material.WOOL).sound(SoundType.CLOTH).hardnessAndResistance(1,1)).setRegistryName("crimson_wool");

    //AUTO-GENERATE CODE |GC-00|
    //END OF AUTO-GENERATE CODE |GC-01|
}