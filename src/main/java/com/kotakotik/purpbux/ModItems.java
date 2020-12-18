package com.kotakotik.purpbux;

import com.kotakotik.purpbux.items.ExpBottle;
import com.kotakotik.purpbux.items.Wallet;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
    public static final RegistryObject<Item> PURP_BUX = Registration.ITEMS.register("purp_bux", () ->
            new Item(new Item.Properties().group(Purpbux.TAB)));

    public static final RegistryObject<Item> WALLET = Registration.ITEMS.register("wallet", () ->
            new Wallet(new Item.Properties().group(Purpbux.TAB)));

    public static final RegistryObject<Item> EXP_BOTTLE = Registration.ITEMS.register("exp_bottle", () ->
            new ExpBottle(new Item.Properties().group(Purpbux.TAB).food(new Food.Builder()
                    .hunger(0)
                    .saturation(0)
                    .setAlwaysEdible()
                    .build())));

    static void register() {
    }
}
