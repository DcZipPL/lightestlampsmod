package tk.dczippl.lightestlamp.plugins.mystical_agriculture;

import com.blakebr0.mysticalagriculture.api.crop.Crop;
import com.blakebr0.mysticalagriculture.api.crop.CropTier;
import com.blakebr0.mysticalagriculture.api.crop.CropType;
import com.blakebr0.mysticalagriculture.api.lib.LazyIngredient;
import net.minecraft.util.ResourceLocation;
import tk.dczippl.lightestlamp.Reference;

import java.awt.*;

public class GlowingDustCrop extends Crop
{
    public GlowingDustCrop()
    {
        super(new ResourceLocation(Reference.MOD_ID,"glowing_dust"), CropTier.THREE, CropType.RESOURCE, LazyIngredient.item("lightestlamp:glowing_dust_agglomeratio"));
    }
}