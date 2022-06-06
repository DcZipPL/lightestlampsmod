package tk.dczippl.lightestlamp.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ObjectHolder;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeTile;
import tk.dczippl.lightestlamp.tile.*;

public class ModTileEntities
{
    @ObjectHolder(Reference.MOD_ID+":"+"antilamp_te")
    public static BlockEntityType<AntiLampTileEntity> ANTILAMP_TE;

    @ObjectHolder(Reference.MOD_ID+":"+"light_air_te")
    public static BlockEntityType<LightAirTileEntity> AIR_TE;

    @ObjectHolder(Reference.MOD_ID+":"+"omega_chunkcleaner_te")
    public static BlockEntityType<OmegaChunkCleanerTileEntity> OCC_TE;

    @ObjectHolder(Reference.MOD_ID+":"+"clear_te")
    public static BlockEntityType<ClearLampTileEntity> CLEAR_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"alfa_te")
    public static BlockEntityType<AlfaLampTileEntity> ALFA_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"beta_te")
    public static BlockEntityType<BetaLampTileEntity> BETA_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"gamma_te")
    public static BlockEntityType<GammaLampTileEntity> GAMMA_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"delta_te")
    public static BlockEntityType<DeltaLampTileEntity> DELTA_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"epsilon_te")
    public static BlockEntityType<EpsilonLampTileEntity> EPSILON_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"zeta_te")
    public static BlockEntityType<ZetaLampTileEntity> ZETA_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"eta_te")
    public static BlockEntityType<EtaLampTileEntity> ETA_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"omega_te")
    public static BlockEntityType<OmegaLampTileEntity> OMEGA_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"clear_sea_lantern_te")
    public static BlockEntityType<ClearSeaLanternTileEntity> CLEARSEALANTERN_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"deep_sea_lantern_te")
    public static BlockEntityType<DeepSeaLanternTileEntity> DEEPSEALANTERN_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"ocean_lantern_te")
    public static BlockEntityType<OceanLanternTileEntity> OCEANLANTERN_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"deep_ocean_lantern_te")
    public static BlockEntityType<DeepOceanLanternTileEntity> DEEPOCEANLANTERN_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"abyssal_lantern_te")
    public static BlockEntityType<AbyssalLanternTileEntity> ABYSSALLANTERN_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"alchemical_lamp_te")
    public static BlockEntityType<AlchemicalLampTileEntity> ALCHEMICALLAMP_TE;
    public static BlockEntityType<GasCentrifugeTile> CENTRIFUGE_TE;
}