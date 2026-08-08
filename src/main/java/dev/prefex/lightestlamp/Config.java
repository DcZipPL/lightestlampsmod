package dev.prefex.lightestlamp;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    public static final String CATEGORY_RESOURCES = "resources";
    public static final String CATEGORY_TWEAKS = "tweaks";

    private static final ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec COMMON_CONFIG;

    public static ModConfigSpec.IntValue GLOWSTONE_FUEL_MULTIPLIER;
    public static ModConfigSpec.EnumValue<EnergyModes> ENERGY_MODE;
    public static ModConfigSpec.IntValue LIGHT_TRAVERSAL_BUDGET;
    public static ModConfigSpec.IntValue LIGHT_MUTATION_BUDGET;
    public static ModConfigSpec.IntValue LIGHT_TOPOLOGY_DEBOUNCE;

    static {
        setupFirstBlockConfig();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    private static void setupFirstBlockConfig() {
        COMMON_BUILDER.comment("Resources settings").push(CATEGORY_RESOURCES);
        GLOWSTONE_FUEL_MULTIPLIER = COMMON_BUILDER.comment("Gas Centrifuge Glowstone fuel multiplier (Bigger multiplier less fuel used) Min: 2, Max: 20, IntValue")
                .defineInRange("glowstone_multiplier", 4, 2, 20);
        COMMON_BUILDER.pop();
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
        COMMON_BUILDER.comment("Extended lamp lighting scheduler").push("lighting");
        LIGHT_TRAVERSAL_BUDGET = COMMON_BUILDER.comment("Maximum source-planning positions visited across all dimensions per server tick.")
                .defineInRange("max_traversed_positions_per_tick", 4096, 256, 65536);
        LIGHT_MUTATION_BUDGET = COMMON_BUILDER.comment("Maximum hidden light-source block changes across all dimensions per server tick.")
                .defineInRange("max_source_updates_per_tick", 128, 16, 4096);
        LIGHT_TOPOLOGY_DEBOUNCE = COMMON_BUILDER.comment("Ticks used to combine repeated wall, fluid, piston, and door updates.")
                .defineInRange("topology_debounce_ticks", 5, 1, 100);
        COMMON_BUILDER.pop();
    }

    public enum EnergyModes{
        passive_only,
        both,
        energy_only,
        no_overclocking,
        no_overclocking_with_passive
    }
}
