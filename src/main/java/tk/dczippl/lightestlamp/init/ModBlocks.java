package tk.dczippl.lightestlamp.init;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.blocks.*;
import tk.dczippl.lightestlamp.machine.craftingtest.WorkbenchBlock;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeBlock;

public class ModBlocks
{
    public static void init(IEventBus modEventBus){
        ModBlocks.BLOCKS.register(modEventBus);
    }

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);

    //public static final RegistryObject<Block> DARK_AIR = BLOCKS.register("dark_air", () -> new DarkAirBlock());
    public static final RegistryObject<Block> LIGHT_AIR = BLOCKS.register("light_air", () -> new LightAirBlock());
    public static final RegistryObject<Block> WATERLOGGABLE_LIGHT_AIR = BLOCKS.register("waterloggable_light_air", () -> new WaterLoggableLightAirBlock());
    public static final RegistryObject<Block> CLEAR_LAMP = BLOCKS.register("clear_lamp", () -> new ClearLampBlock(Block.Properties.create(Material.REDSTONE_LIGHT).sound(SoundType.GLASS)
            .hardnessAndResistance(0.3f,1)));
    public static final RegistryObject<Block> ALPHA_LAMP = BLOCKS.register("alpha_lamp", () -> new AlfaLampBlock());
    public static final RegistryObject<Block> BETA_LAMP = BLOCKS.register("beta_lamp", () -> new BetaLampBlock());
    public static final RegistryObject<Block> GAMMA_LAMP = BLOCKS.register("gamma_lamp", () -> new GammaLampBlock());
    public static final RegistryObject<Block> DELTA_LAMP = BLOCKS.register("delta_lamp", () -> new DeltaLampBlock());
    public static final RegistryObject<Block> EPSILON_LAMP = BLOCKS.register("epsilon_lamp", () -> new EpsilonLampBlock());
    public static final RegistryObject<Block> ZETA_LAMP = BLOCKS.register("zeta_lamp", () -> new ZetaLampBlock());
    public static final RegistryObject<Block> ETA_LAMP = BLOCKS.register("eta_lamp", () -> new EtaLampBlock());
    public static final RegistryObject<Block> OMEGA_LAMP = BLOCKS.register("omega_lamp", () -> new OmegaLampBlock());
    public static final RegistryObject<Block> CLEAR_SEA_LANTERN = BLOCKS.register("clear_sea_lantern", () -> new ClearSeaLanternBlock());
    public static final RegistryObject<Block> DEEP_SEA_LANTERN = BLOCKS.register("deep_sea_lantern", () -> new DeepSeaLanternBlock());
    public static final RegistryObject<Block> OCEAN_LANTERN = BLOCKS.register("ocean_lantern", () -> new OceanLanternBlock());
    public static final RegistryObject<Block> DEEP_OCEAN_LANTERN = BLOCKS.register("deep_ocean_lantern", () -> new DeepOceanLanternBlock());
    public static final RegistryObject<Block> ABYSSAL_LANTERN = BLOCKS.register("abyssal_lantern", () -> new AbyssalLanternBlock());
    public static final RegistryObject<Block> ALCHEMICAL_LAMP = BLOCKS.register("alchemical_lamp", () -> new AlchemicalLampBlock());
    //public static final RegistryObject<Block> SPOTLIGHT = BLOCKS.register("spotlight", () -> new AlchemicalLampBlock());
    public static final RegistryObject<Block> CHUNK_CLEANER = BLOCKS.register("debug_chunk_cleaner", () -> new ChunkCleanerBlock(Block.Properties.create(Material.WOOL)));

    //Other lamps

    public static final RegistryObject<Block> ANTI_LAMP = BLOCKS.register("anti_lamp", () -> new AntiLampBlock());
    public static final RegistryObject<Block> JUNGLE_LANTERN = BLOCKS.register("jungle_lantern", () -> new JungleLanternBlock(Block.Properties.create(Material.LEAVES).sound(SoundType.PLANT)
            .hardnessAndResistance(0.2f,1)));

    //Other Blocks

    public static final RegistryObject<Block> NEON_ROD_BLOCK = BLOCKS.register("neon_rod_block", () -> new GlowingRotatedPillarBlock(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS)
            .hardnessAndResistance(0.85f,1),10));
    public static final RegistryObject<Block> ARGON_ROD_BLOCK = BLOCKS.register("argon_rod_block", () -> new GlowingRotatedPillarBlock(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS)
            .hardnessAndResistance(0.85f,1),10));
    public static final RegistryObject<Block> KRYPTON_ROD_BLOCK = BLOCKS.register("krypton_rod_block", () -> new GlowingRotatedPillarBlock(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS)
            .hardnessAndResistance(0.85f,1),14));
    public static final RegistryObject<Block> XENON_ROD_BLOCK = BLOCKS.register("xenon_rod_block", () -> new GlowingRotatedPillarBlock(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS)
            .hardnessAndResistance(0.85f,1),14));
    public static final RegistryObject<Block> RADON_ROD_BLOCK = BLOCKS.register("radon_rod_block", () -> new GlowingRotatedPillarBlock(Block.Properties.create(Material.GLASS).sound(SoundType.GLASS)
            .hardnessAndResistance(0.85f,1),15));
    public static final RegistryObject<Block> VANTA_BLACK = BLOCKS.register("vanta_black", () -> new Block(Block.Properties.create(Material.WOOL).sound(SoundType.CLOTH)
            .hardnessAndResistance(0.1f,1)));
    public static final RegistryObject<Block> MONAZITE_ORE = BLOCKS.register("monazite_ore", () -> new SolidOreBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)
            .hardnessAndResistance(6.4f,1),2));
    public static final RegistryObject<Block> LUMINATIUM_BLOCK = BLOCKS.register("luminatium_block", () -> new GlowingBlock(Block.Properties.create(Material.REDSTONE_LIGHT).sound(SoundType.GLASS)
            .hardnessAndResistance(0.4f,1),15));

    //Glowstones
    public static final RegistryObject<Block> NEON_BLOCK = BLOCKS.register("neon_block", () -> new GlowingBlock(Block.Properties.create(Material.REDSTONE_LIGHT).sound(SoundType.GLASS)
            .hardnessAndResistance(0.4f,1),15));
    public static final RegistryObject<Block> ARGON_BLOCK = BLOCKS.register("argon_block", () -> new GlowingBlock(Block.Properties.create(Material.REDSTONE_LIGHT).sound(SoundType.GLASS)
            .hardnessAndResistance(0.4f,1),15));
    public static final RegistryObject<Block> KRYPTON_BLOCK = BLOCKS.register("krypton_block", () -> new GlowingBlock(Block.Properties.create(Material.REDSTONE_LIGHT).sound(SoundType.GLASS)
            .hardnessAndResistance(0.4f,1),15));
    public static final RegistryObject<Block> XENON_BLOCK = BLOCKS.register("xenon_block", () -> new GlowingBlock(Block.Properties.create(Material.REDSTONE_LIGHT).sound(SoundType.GLASS)
            .hardnessAndResistance(0.4f,1),15));
    public static final RegistryObject<Block> RADON_BLOCK = BLOCKS.register("radon_block", () -> new GlowingBlock(Block.Properties.create(Material.REDSTONE_LIGHT).sound(SoundType.GLASS)
            .hardnessAndResistance(0.4f,1),15));
    public static final RegistryObject<Block> GLOWING_GLASS_BLOCK = BLOCKS.register("glowing_glass_block", () -> new GlowingGlassBlock());
    public static final RegistryObject<Block> GAS_EXTRACTOR = BLOCKS.register("gas_centrifuge", () -> new GasCentrifugeBlock(Block.Properties.create(Material.IRON).sound(SoundType.METAL)
    .hardnessAndResistance(3,1)));

    public static final RegistryObject<Block> OCC = BLOCKS.register("occ", () -> new OmegaChunkCleanerBlock(Block.Properties.create(Material.IRON)));

    public static final RegistryObject<Block> WORKBENCH = BLOCKS.register("workbench", () -> new WorkbenchBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)
            .hardnessAndResistance(0.3f,1)));
    public static final RegistryObject<Block> FLUID_WORKBENCH = BLOCKS.register("fluid_workbench", () -> new WorkbenchBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)
            .hardnessAndResistance(0.3f,1)));
    public static final RegistryObject<Block> AUTO_WORKBENCH = BLOCKS.register("auto_workbench", () -> new Block(Block.Properties.create(Material.IRON).sound(SoundType.METAL)
            .hardnessAndResistance(0.3f,1)));
    public static final RegistryObject<Block> SMITING_BENCH = BLOCKS.register("smithingbench", () -> new Block(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)
            .hardnessAndResistance(0.3f,1)));
    public static final RegistryObject<Block> AUTO_SMITING_BENCH = BLOCKS.register("auto_smithingbench", () -> new Block(Block.Properties.create(Material.IRON).sound(SoundType.METAL)
            .hardnessAndResistance(0.3f,1)));
    public static final RegistryObject<Block> STONECUTTER = BLOCKS.register("stonecutter", () -> new Block(Block.Properties.create(Material.ROCK).sound(SoundType.STONE)
            .hardnessAndResistance(0.3f,1)));
}