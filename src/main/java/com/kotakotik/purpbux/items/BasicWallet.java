package com.kotakotik.purpbux.items;

import com.kotakotik.purpbux.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;

import javax.annotation.Nonnull;

public class BasicWallet extends AbstractWallet {
    @Override
    int getWalletnum() {
        return 1;
    }

    @Override
    public Item getPreviousWallet() {
        return null;
    }

    @Nonnull
    @Override
    public Item getMaterial() {
        return Items.LEATHER;
    }

    @Override
    public String getWalletName() {
        return "basic";
    }

    @Override
    public IItemProvider getProvider() {
        return ModItems.WALLET.get();
    }
}
