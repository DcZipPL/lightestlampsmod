package tk.dczippl.lightestlamp.plugins;

import com.blakebr0.mysticalagriculture.api.IMysticalAgriculturePlugin;
import com.blakebr0.mysticalagriculture.api.MysticalAgriculturePlugin;
import com.blakebr0.mysticalagriculture.api.crop.Crop;
import com.blakebr0.mysticalagriculture.api.registry.ICropRegistry;
import tk.dczippl.lightestlamp.plugins.mystical_agriculture.GlowingDustCrop;

import java.awt.*;

@MysticalAgriculturePlugin
public class MAPlugin implements IMysticalAgriculturePlugin
{
    @Override
    public void onRegisterCrops(ICropRegistry registry)
    {
        Crop GLOWING_DUST_CROP = new GlowingDustCrop().setColor(new Color(200,200,50).getRGB());
        registry.register(GLOWING_DUST_CROP);
    }
}