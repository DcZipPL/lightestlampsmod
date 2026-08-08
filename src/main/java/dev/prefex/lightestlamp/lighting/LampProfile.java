package dev.prefex.lightestlamp.lighting;

/** Immutable behavior shared by the block, planner, and migration code. */
public record LampProfile(int extension, Medium medium, boolean luminant, boolean alwaysActive) {
    public static final int SOURCE_SPACING = 3;

    public LampProfile {
        if (extension < 1 || extension > 15) {
            throw new IllegalArgumentException("Lamp extension must be in the vanilla light range: " + extension);
        }
    }

    public int advertisedRange() {
        return 15 + extension;
    }

    public boolean shouldPlaceSourceAt(int distance) {
        return distance > 0 && (distance == extension || distance % SOURCE_SPACING == 0);
    }

    public int maximumOpenSourceCount() {
        int sources = 0;
        for (int distance = 1; distance <= extension; distance++) {
            if (shouldPlaceSourceAt(distance)) {
                sources += 4 * distance * distance + 2;
            }
        }
        return sources;
    }

    public enum Medium {
        AIR,
        UNDERWATER
    }
}
