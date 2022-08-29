package tk.dczippl.lightestlamp.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeBlockEntity;
import tk.dczippl.lightestlamp.entities.*;
import tk.dczippl.lightestlamp.entities.cleaners.ChunkCleanerBlockEntity;
import tk.dczippl.lightestlamp.entities.cleaners.OmegaChunkCleanerBlockEntity;

public class ModBlockEntities
{
    public static BlockEntityType<ChunkCleanerBlockEntity> CC_BE;
    public static BlockEntityType<OmegaChunkCleanerBlockEntity> OCC_BE;
    public static BlockEntityType<NormalLampBlockEntity> NORMAL_LAMP_BE;
    public static BlockEntityType<OmegaLampBlockEntity> OMEGA_BE;
    public static BlockEntityType<AlchemicalLampBlockEntity> ALCHEMICALLAMP_TE;
    public static BlockEntityType<GasCentrifugeBlockEntity> CENTRIFUGE_TE;
}