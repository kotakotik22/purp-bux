package com.kotakotik.purpbux.jei.recipes.purpbux;

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
