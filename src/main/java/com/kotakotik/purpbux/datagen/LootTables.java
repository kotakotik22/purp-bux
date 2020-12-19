package com.kotakotik.purpbux.datagen;

import com.kotakotik.purpbux.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.RegistryObject;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        basicTable(ModBlocks.BUX_PILE);
        basicTable(ModBlocks.BUX_STATION);
        basicTable(ModBlocks.EXP_EXTRACTOR);
        basicTable(ModBlocks.SVELTEBUX_GRAVESTONE.get());
    }

    private void basicTable(Block block) {
        lootTables.put(block, createStandardTable(block.asItem().getName().getString(), block));
    }

    private void basicTable(RegistryObject<Block> block) {
        basicTable(block.get());
    }
}
