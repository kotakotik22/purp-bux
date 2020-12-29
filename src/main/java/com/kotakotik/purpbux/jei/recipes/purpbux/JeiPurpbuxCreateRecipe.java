package com.kotakotik.purpbux.jei.recipes.purpbux;

import net.minecraft.item.Item;

public class JeiPurpbuxCreateRecipe {
    private final Item item1;
    private final Item item2;

    public JeiPurpbuxCreateRecipe(Item item1, Item item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    public Item getItem1() {
        return item1;
    }

    public Item getItem2() {
        return item2;
    }
}