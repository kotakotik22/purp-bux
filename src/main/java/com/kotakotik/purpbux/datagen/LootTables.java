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
        basicNBTTable(ModBlocks.BUX_STATION, "inputInv", "buxInv");
        basicTable(ModBlocks.EXP_EXTRACTOR);
        basicTable(ModBlocks.SVELTEBUX_GRAVESTONE); // yes i removed the .get() and i know that it doesnt change anything, i just did it so it looks better
    }

    private void basicNBTTable(Block block, String... NBT) {
        lootTables.put(block, createStandardNBTTable(block.asItem().getName().getString(), block, NBT));
    }

    private void basicNBTTable(RegistryObject<Block> block, String... NBT) {
        basicNBTTable(block.get(), NBT);
    }

    private void basicTable(Block block) {
        lootTables.put(block, createStandardTable(block.asItem().getName().getString(), block));
    }

    private void basicTable(RegistryObject<Block> block) {
        basicTable(block.get());
    }
}
