package tk.dczippl.lightestlamp.init;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.machine.gascentrifuge.GasCentrifugeTile;
import tk.dczippl.lightestlamp.tile.*;

public class ModTileEntities
{
    @ObjectHolder(Reference.MOD_ID+":"+"antilamp_te")
    public static TileEntityType<AntiLampTileEntity> ANTILAMP_TE;

    @ObjectHolder(Reference.MOD_ID+":"+"light_air_te")
    public static TileEntityType<LightAirTileEntity> AIR_TE;

    @ObjectHolder(Reference.MOD_ID+":"+"omega_chunkcleaner_te")
    public static TileEntityType<OmegaChunkCleanerTileEntity> OCC_TE;

    @ObjectHolder(Reference.MOD_ID+":"+"clear_te")
    public static TileEntityType<ClearLampTileEntity> CLEAR_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"alfa_te")
    public static TileEntityType<AlfaLampTileEntity> ALFA_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"beta_te")
    public static TileEntityType<BetaLampTileEntity> BETA_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"gamma_te")
    public static TileEntityType<GammaLampTileEntity> GAMMA_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"delta_te")
    public static TileEntityType<DeltaLampTileEntity> DELTA_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"epsilon_te")
    public static TileEntityType<EpsilonLampTileEntity> EPSILON_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"zeta_te")
    public static TileEntityType<ZetaLampTileEntity> ZETA_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"eta_te")
    public static TileEntityType<EtaLampTileEntity> ETA_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"omega_te")
    public static TileEntityType<OmegaLampTileEntity> OMEGA_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"clear_sea_lantern_te")
    public static TileEntityType<ClearSeaLanternTileEntity> CLEARSEALANTERN_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"deep_sea_lantern_te")
    public static TileEntityType<DeepSeaLanternTileEntity> DEEPSEALANTERN_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"ocean_lantern_te")
    public static TileEntityType<OceanLanternTileEntity> OCEANLANTERN_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"deep_ocean_lantern_te")
    public static TileEntityType<DeepOceanLanternTileEntity> DEEPOCEANLANTERN_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"abyssal_lantern_te")
    public static TileEntityType<AbyssalLanternTileEntity> ABYSSALLANTERN_TE;
    @ObjectHolder(Reference.MOD_ID+":"+"alchemical_lamp_te")
    public static TileEntityType<AlchemicalLampTileEntity> ALCHEMICALLAMP_TE;
    public static TileEntityType<GasCentrifugeTile> CENTRIFUGE_TE;
}