package tk.dczippl.lightestlamp;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {

    public static final String CATEGORY_WORLDGEN = "worldgen";
    public static final String CATEGORY_RESOURCES = "resources";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.ConfigValue<Integer> GLOWSTONE_FUEL_MULTIPLIER;
    public static ForgeConfigSpec.BooleanValue MONAZITE_SPAWN;


    static {
        setupFirstBlockConfig();

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupFirstBlockConfig() {
        COMMON_BUILDER.comment("Resources settings").push(CATEGORY_RESOURCES);
        GLOWSTONE_FUEL_MULTIPLIER = COMMON_BUILDER.comment("Gas Centrifuge Glowstone fuel multiplier (Bigger multiplier less fuel used) Min: 2, Max: 20, IntValue")
                .define("glowstone_multiplier",4);
        COMMON_BUILDER.pop();
        COMMON_BUILDER.comment("World Generation settings").push(CATEGORY_WORLDGEN);

        MONAZITE_SPAWN = COMMON_BUILDER.comment("Generation of Monazite Ore in nether")
                .define("monazite_gen",true);

        COMMON_BUILDER.pop();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }
}