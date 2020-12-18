package com.kotakotik.purpbux;

import com.kotakotik.purpbux.blocks.BuxPile;
import com.kotakotik.purpbux.blocks.BuxStation;
import com.kotakotik.purpbux.blocks.ExpExtractor;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final RegistryObject<Block> BUX_STATION = register("bux_station", BuxStation::new, 1);
    public static final RegistryObject<Block> EXP_EXTRACTOR = register("exp_extractor", ExpExtractor::new, 1);
    public static final RegistryObject<Block> BUX_PILE = register("bux_pile", BuxPile::new, 1);

    static void register() {
    }

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registration.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, int maxStackSize) {
        RegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().group(Purpbux.TAB).maxStackSize(maxStackSize)));
        return ret;
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        return register(name, block, 0);
    }
}
