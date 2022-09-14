package dev.prefex.lightestlamp.init;

import dev.prefex.lightestlamp.Util;
import dev.prefex.lightestlamp.entities.AlchemicalLampBlockEntity;
import dev.prefex.lightestlamp.entities.NormalLampBlockEntity;
import dev.prefex.lightestlamp.entities.OmegaLampBlockEntity;
import dev.prefex.lightestlamp.entities.cleaners.OmegaChunkCleanerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeBlockEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities
{
    public static void init(IEventBus modEventBus){
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
    }
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Util.MOD_ID);

    public static final RegistryObject<BlockEntityType<OmegaChunkCleanerBlockEntity>> OCC_BE = BLOCK_ENTITIES.register(
            "occ_be", () -> BlockEntityType.Builder.of(OmegaChunkCleanerBlockEntity::new, ModBlocks.OCC.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<NormalLampBlockEntity>> NORMAL_LAMP_BE = BLOCK_ENTITIES.register(
            "normal_lamp_be", () -> BlockEntityType.Builder.of(NormalLampBlockEntity::new,
                    ModBlocks.ALPHA_LAMP.get(),
                    ModBlocks.BETA_LAMP.get(),
                    ModBlocks.GAMMA_LAMP.get(),
                    ModBlocks.DELTA_LAMP.get(),
                    ModBlocks.EPSILON_LAMP.get(),
                    ModBlocks.ZETA_LAMP.get(),
                    ModBlocks.ETA_LAMP.get(),
                    ModBlocks.OMEGA_LAMP.get(),
                    ModBlocks.CLEAR_SEA_LANTERN.get(),
                    ModBlocks.DEEP_SEA_LANTERN.get(),
                    ModBlocks.OCEAN_LANTERN.get(),
                    ModBlocks.DEEP_OCEAN_LANTERN.get(),
                    ModBlocks.ABYSSAL_LANTERN.get()
            ).build(null)
    );
    public static final RegistryObject<BlockEntityType<OmegaLampBlockEntity>> OMEGA_BE = BLOCK_ENTITIES.register(
            "omega_be", () -> BlockEntityType.Builder.of(OmegaLampBlockEntity::new, ModBlocks.OMEGA_LAMP.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<AlchemicalLampBlockEntity>> ALCHEMICALLAMP_BE = BLOCK_ENTITIES.register(
            "alchemical_be", () -> BlockEntityType.Builder.of(AlchemicalLampBlockEntity::new, ModBlocks.ALCHEMICAL_LAMP.get()).build(null)
    );
    public static final RegistryObject<BlockEntityType<GasCentrifugeBlockEntity>> CENTRIFUGE_BE = BLOCK_ENTITIES.register(
            "centrifuge_be", () -> BlockEntityType.Builder.of(GasCentrifugeBlockEntity::new, ModBlocks.GLOWSTONE_CENTRIFUGE.get()).build(null)
    );
}