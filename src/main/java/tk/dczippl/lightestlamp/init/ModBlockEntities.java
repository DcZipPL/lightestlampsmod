package tk.dczippl.lightestlamp.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import tk.dczippl.lightestlamp.tile.*;
import tk.dczippl.lightestlamp.tile.cleaners.ChunkCleanerBlockEntity;
import tk.dczippl.lightestlamp.tile.cleaners.OmegaChunkCleanerTileEntity;

public class ModBlockEntities
{
    public static BlockEntityType<ChunkCleanerBlockEntity> AIR_TE;

    public static BlockEntityType<OmegaChunkCleanerTileEntity> OCC_TE;
    public static BlockEntityType<NormalLampBlockEntity> NORMAL_LAMP_BE;
    public static BlockEntityType<UnderwaterLampBlockEntity> UNDERWATER_LAMP_BE;
    public static BlockEntityType<OmegaLampTileEntity> OMEGA_BE;
    public static BlockEntityType<ClearSeaLanternTileEntity> CLEARSEALANTERN_TE;
    public static BlockEntityType<DeepSeaLanternTileEntity> DEEPSEALANTERN_TE;
    public static BlockEntityType<OceanLanternTileEntity> OCEANLANTERN_TE;
    public static BlockEntityType<DeepOceanLanternTileEntity> DEEPOCEANLANTERN_TE;
    public static BlockEntityType<AbyssalLanternBlockEntity> ABYSSALLANTERN_TE;
    public static BlockEntityType<AlchemicalLampBlockEntity> ALCHEMICALLAMP_TE;
    public static BlockEntityType<GasCentrifugeBlockEntity> CENTRIFUGE_TE;
}