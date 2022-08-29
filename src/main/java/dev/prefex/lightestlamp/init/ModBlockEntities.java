package dev.prefex.lightestlamp.init;

import dev.prefex.lightestlamp.entities.AlchemicalLampBlockEntity;
import dev.prefex.lightestlamp.entities.NormalLampBlockEntity;
import dev.prefex.lightestlamp.entities.OmegaLampBlockEntity;
import dev.prefex.lightestlamp.entities.cleaners.OmegaChunkCleanerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import dev.prefex.lightestlamp.machine.gascentrifuge.GasCentrifugeBlockEntity;
import tk.dczippl.lightestlamp.entities.*;
import dev.prefex.lightestlamp.entities.cleaners.ChunkCleanerBlockEntity;

public class ModBlockEntities
{
    public static BlockEntityType<ChunkCleanerBlockEntity> CLEANER_BE;
    public static BlockEntityType<OmegaChunkCleanerBlockEntity> OCC_BE;
    public static BlockEntityType<NormalLampBlockEntity> NORMAL_LAMP_BE;
    public static BlockEntityType<OmegaLampBlockEntity> OMEGA_BE;
    public static BlockEntityType<AlchemicalLampBlockEntity> ALCHEMICALLAMP_BE;
    public static BlockEntityType<GasCentrifugeBlockEntity> CENTRIFUGE_BE;
}