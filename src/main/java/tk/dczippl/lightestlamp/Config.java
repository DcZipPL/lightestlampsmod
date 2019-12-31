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

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;


    public static ForgeConfigSpec.BooleanValue NEON_GLOWSTONE_SPAWN;
    public static ForgeConfigSpec.BooleanValue ARGON_GLOWSTONE_SPAWN;
    public static ForgeConfigSpec.BooleanValue KRYPTON_GLOWSTONE_SPAWN;


    static {
        setupFirstBlockConfig();

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupFirstBlockConfig() {
        COMMON_BUILDER.comment("World Generation settings").push(CATEGORY_WORLDGEN);

        NEON_GLOWSTONE_SPAWN = COMMON_BUILDER.comment("Generation of Neon Glowstone in nether")
                .define("neon_gen",true);
        ARGON_GLOWSTONE_SPAWN = COMMON_BUILDER.comment("Generation of Argon Glowstone in nether")
                .define("argon_gen",true);
        KRYPTON_GLOWSTONE_SPAWN = COMMON_BUILDER.comment("Generation of Krypton Glowstone in nether")
                .define("krypton_gen",true);

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

    @SubscribeEvent
    public static void onReload(final ModConfig.ConfigReloading configEvent) {
    }
}