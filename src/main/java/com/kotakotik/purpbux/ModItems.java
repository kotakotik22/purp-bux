package com.kotakotik.purpbux;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
    public static final RegistryObject<Item> PURP_BUX = Registration.ITEMS.register("purp_bux", () ->
            new Item(new Item.Properties().group(ItemGroup.MATERIALS)));

    static void register() {}
}
