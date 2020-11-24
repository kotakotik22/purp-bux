package com.kotakotik.purpbux.datagen;

import com.kotakotik.purpbux.ModBlocks;
import net.minecraft.data.DataGenerator;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        lootTables.put(ModBlocks.BUX_STATION.get(), createStandardTable("bux_station", ModBlocks.BUX_STATION.get()));
    }
}
