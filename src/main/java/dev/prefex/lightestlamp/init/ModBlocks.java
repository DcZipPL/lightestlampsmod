package dev.prefex.lightestlamp.init;

import dev.prefex.lightestlamp.blocks.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeBlock;

import static dev.prefex.lightestlamp.blocks.LightestLampBlock.Type.NORMAL;
import static dev.prefex.lightestlamp.blocks.LightestLampBlock.Type.UNDERWATER;

public class ModBlocks
{
    public static void init(IEventBus modEventBus){
        ModBlocks.BLOCKS.register(modEventBus);
    }

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Util.MOD_ID);
    public static final RegistryObject<Block> LIGHT_AIR = BLOCKS.register("light_air", LightAirBlock::new);
    public static final RegistryObject<Block> WATERLOGGABLE_LIGHT_AIR = BLOCKS.register("waterloggable_light_air", WaterLoggableLightAirBlock::new);
    public static final RegistryObject<Block> CLEAR_LAMP = BLOCKS.register("clear_lamp", () -> new ClearLampBlock(Block.Properties.copy(Blocks.REDSTONE_LAMP).sound(SoundType.GLASS)
            .strength(0.3f,1)));
    public static final RegistryObject<Block> ALPHA_LAMP = BLOCKS.register("alpha_lamp", () -> new LightestLampBlock(NORMAL, LightestLampBlock.Tier.ALPHA));
    public static final RegistryObject<Block> BETA_LAMP = BLOCKS.register("beta_lamp", () -> new LightestLampBlock(NORMAL, LightestLampBlock.Tier.BETA));
    public static final RegistryObject<Block> GAMMA_LAMP = BLOCKS.register("gamma_lamp", () -> new LightestLampBlock(NORMAL, LightestLampBlock.Tier.GAMMA));
    public static final RegistryObject<Block> DELTA_LAMP = BLOCKS.register("delta_lamp", () -> new LightestLampBlock(NORMAL, LightestLampBlock.Tier.DELTA));
    public static final RegistryObject<Block> EPSILON_LAMP = BLOCKS.register("epsilon_lamp", () -> new LightestLampBlock(NORMAL, LightestLampBlock.Tier.EPSILON));
    public static final RegistryObject<Block> ZETA_LAMP = BLOCKS.register("zeta_lamp", () -> new LightestLampBlock(NORMAL, LightestLampBlock.Tier.ZETA));
    public static final RegistryObject<Block> ETA_LAMP = BLOCKS.register("eta_lamp", () -> new LightestLampBlock(NORMAL, LightestLampBlock.Tier.ETA));
    public static final RegistryObject<Block> OMEGA_LAMP = BLOCKS.register("omega_lamp", OmegaLampBlock::new);
    public static final RegistryObject<Block> CLEAR_SEA_LANTERN = BLOCKS.register("clear_sea_lantern", () -> new LightestLampBlock(UNDERWATER, LightestLampBlock.Tier.ALPHA));
    public static final RegistryObject<Block> DEEP_SEA_LANTERN = BLOCKS.register("deep_sea_lantern", () -> new LightestLampBlock(UNDERWATER, LightestLampBlock.Tier.GAMMA));
    public static final RegistryObject<Block> OCEAN_LANTERN = BLOCKS.register("ocean_lantern", () -> new LightestLampBlock(UNDERWATER, LightestLampBlock.Tier.GAMMA));
    public static final RegistryObject<Block> DEEP_OCEAN_LANTERN = BLOCKS.register("deep_ocean_lantern", () -> new LightestLampBlock(UNDERWATER, LightestLampBlock.Tier.EPSILON));
    public static final RegistryObject<Block> ABYSSAL_LANTERN = BLOCKS.register("abyssal_lantern", () -> new LightestLampBlock(UNDERWATER, LightestLampBlock.Tier.ETA));
    public static final RegistryObject<Block> ALCHEMICAL_LAMP = BLOCKS.register("alchemical_lamp", AlchemicalLampBlock::new);
    //public static final RegistryObject<Block> SPOTLIGHT = BLOCKS.register("spotlight", () -> new SpotlightBlock());
    //public static final RegistryObject<Block> CHUNK_CLEANER = BLOCKS.register("debug_chunk_cleaner", () -> new ChunkCleanerBlock(Block.Properties.of(Material.WOOL)));

    //Other lamps
    public static final RegistryObject<Block> JUNGLE_LANTERN = BLOCKS.register("jungle_lantern", () -> new JungleLanternBlock(Block.Properties.copy(Blocks.OAK_LEAVES).sound(SoundType.GRASS)
            .strength(0.2f,1)));

    //Other Blocks

    public static final RegistryObject<Block> NEON_TUBE_BLOCK = BLOCKS.register("neon_tube_block", () -> new GlowingRotatedPillarBlock(Block.Properties.copy(Blocks.GLOWSTONE).sound(SoundType.GLASS)
            .strength(0.85f,1),10));
    public static final RegistryObject<Block> ARGON_TUBE_BLOCK = BLOCKS.register("argon_tube_block", () -> new GlowingRotatedPillarBlock(Block.Properties.copy(Blocks.GLOWSTONE).sound(SoundType.GLASS)
            .strength(0.85f,1),10));
    public static final RegistryObject<Block> KRYPTON_TUBE_BLOCK = BLOCKS.register("krypton_tube_block", () -> new GlowingRotatedPillarBlock(Block.Properties.copy(Blocks.GLOWSTONE).sound(SoundType.GLASS)
            .strength(0.85f,1),14));
    public static final RegistryObject<Block> XENON_TUBE_BLOCK = BLOCKS.register("xenon_tube_block", () -> new GlowingRotatedPillarBlock(Block.Properties.copy(Blocks.GLOWSTONE).sound(SoundType.GLASS)
            .strength(0.85f,1),14));
    public static final RegistryObject<Block> RADON_TUBE_BLOCK = BLOCKS.register("radon_tube_block", () -> new GlowingRotatedPillarBlock(Block.Properties.copy(Blocks.GLOWSTONE).sound(SoundType.GLASS)
            .strength(0.85f,1),15));
    public static final RegistryObject<Block> VANTA_BLACK = BLOCKS.register("vanta_black", () -> new Block(Block.Properties.copy(Blocks.BLACK_WOOL).sound(SoundType.WOOL)
            .strength(0.1f,1)));
    public static final RegistryObject<Block> CURTAIN_BLOCK = BLOCKS.register("curtain_block", CurtainBlock::new);
    public static final RegistryObject<Block> LANTHANUM_ORE = BLOCKS.register("lanthanum_ore", () -> new Block(Block.Properties.copy(Blocks.NETHERRACK).sound(SoundType.NETHERRACK)
            .strength(6.4f,1).requiresCorrectToolForDrops()));

    //Glowstones
    public static final RegistryObject<Block> NEON_BLOCK = BLOCKS.register("neon_block", () -> new GlowingBlock(Block.Properties.copy(Blocks.GLOWSTONE).sound(SoundType.GLASS)
            .strength(0.4f,1),15));
    public static final RegistryObject<Block> ARGON_BLOCK = BLOCKS.register("argon_block", () -> new GlowingBlock(Block.Properties.copy(Blocks.GLOWSTONE).sound(SoundType.GLASS)
            .strength(0.4f,1),15));
    public static final RegistryObject<Block> KRYPTON_BLOCK = BLOCKS.register("krypton_block", () -> new GlowingBlock(Block.Properties.copy(Blocks.GLOWSTONE).sound(SoundType.GLASS)
            .strength(0.4f,1),15));
    public static final RegistryObject<Block> XENON_BLOCK = BLOCKS.register("xenon_block", () -> new GlowingBlock(Block.Properties.copy(Blocks.GLOWSTONE).sound(SoundType.GLASS)
            .strength(0.4f,1),15));
    public static final RegistryObject<Block> RADON_BLOCK = BLOCKS.register("radon_block", () -> new GlowingBlock(Block.Properties.copy(Blocks.GLOWSTONE).sound(SoundType.GLASS)
            .strength(0.4f,1),15));
    public static final RegistryObject<Block> GLOWING_GLASS_BLOCK = BLOCKS.register("glowing_glass_block", GlowingGlassBlock::new);
    public static final RegistryObject<Block> GLOWSTONE_CENTRIFUGE = BLOCKS.register("gas_centrifuge", () -> new GasCentrifugeBlock(Block.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)
    .strength(3,1).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> OCC = BLOCKS.register("occ", () -> new OmegaChunkCleanerBlock(Block.Properties.copy(Blocks.STRUCTURE_BLOCK)));
}