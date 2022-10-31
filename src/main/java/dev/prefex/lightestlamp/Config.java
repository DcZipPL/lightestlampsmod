package dev.prefex.lightestlamp;

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
    public static final String CATEGORY_TWEAKS = "tweaks";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.ConfigValue<Integer> GLOWSTONE_FUEL_MULTIPLIER;
    public static ForgeConfigSpec.BooleanValue MONAZITE_SPAWN;
    public static ForgeConfigSpec.EnumValue<EnergyModes> ENERGY_MODE;

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

        COMMON_BUILDER.comment("Tweaks").push(CATEGORY_TWEAKS);
        ENERGY_MODE = COMMON_BUILDER.comment("Energy mode of Gas Centrifuge machine.",
                        "(passive_only) => Allow only passive mode, machine doesn't require energy and remove passive mode speed penalty. Good for Vanilla+ modpacks.",
                        "(both) => (Default) Allow passive, normal and overclock mode. Passive mode speed penalty enabled. Not recommended.",
                        "(energy_only) => Disable passive mode and allow normal and overclocked mode. Good for modpacks containing tech mods.",
                        "(no_overclocking) => Disable overclocking. Only mode enabled is normal mode.",
                        "(no_overclocking_with_passive) => Only overclocked mode and passive, passive mode speed penalty is still enabled. Why?"
                )
                .defineEnum("energy_mode",EnergyModes.both);

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

    public enum EnergyModes{
        passive_only,
        both,
        energy_only,
        no_overclocking,
        no_overclocking_with_passive
    }
}