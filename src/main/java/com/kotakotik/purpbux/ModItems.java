package com.kotakotik.purpbux;

import com.kotakotik.purpbux.items.ExpBottle;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
    public static final RegistryObject<Item> PURP_BUX = Registration.ITEMS.register("purp_bux", () ->
            new Item(new Item.Properties().group(ItemGroup.MATERIALS)));

    public static final RegistryObject<Item> EXP_BOTTLE = Registration.ITEMS.register("exp_bottle", () ->
            new ExpBottle(new Item.Properties().group(ItemGroup.BREWING).food(new Food.Builder()
                    .hunger(0)
                    .saturation(0)
                    .setAlwaysEdible()
                    .build())));

    static void register() {
    }
}
