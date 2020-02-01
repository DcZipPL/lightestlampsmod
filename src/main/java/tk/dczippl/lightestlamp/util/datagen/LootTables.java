package tk.dczippl.lightestlamp.util.datagen;

import net.minecraft.data.DataGenerator;
import tk.dczippl.lightestlamp.init.ModBlocks;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        lootTables.put(ModBlocks.ANTI_LAMP, createStandardTable("anti_lamp", ModBlocks.ANTI_LAMP));
    }
}
