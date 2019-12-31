package tk.dczippl.lightestlamp.init;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;
import tk.dczippl.lightestlamp.Reference;
import tk.dczippl.lightestlamp.tile.*;

public class ModTileEntities
{
    @ObjectHolder(Reference.MOD_ID+":"+"antilamp_te")
    public static TileEntityType<AntiLampTileEntity> ANTILAMP_TE;

    @ObjectHolder(Reference.MOD_ID+":"+"light_air_te")
    public static TileEntityType<LightAirTileEntity> AIR_TE;

    public static TileEntityType<AlfaLampTileEntity> ALFA_TE;
    public static TileEntityType<BetaLampTileEntity> BETA_TE;
    public static TileEntityType<GammaLampTileEntity> GAMMA_TE;
    public static TileEntityType<DeltaLampTileEntity> DELTA_TE;
    public static TileEntityType<EpsilonLampTileEntity> EPSILON_TE;
    public static TileEntityType<ZetaLampTileEntity> ZETA_TE;
    public static TileEntityType<OmegaLampTileEntity> OMEGA_TE;
    //public static TileEntityType<GasExtractorTileEntity> EXTRACTOR_TE;
}