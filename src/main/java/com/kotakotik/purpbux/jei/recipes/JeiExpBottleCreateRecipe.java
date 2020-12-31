package com.kotakotik.purpbux.jei.recipes;

import net.minecraft.item.Item;

public class JeiExpBottleCreateRecipe {
    private final Item item;

    public JeiExpBottleCreateRecipe(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
