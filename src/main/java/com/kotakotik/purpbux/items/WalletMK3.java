package com.kotakotik.purpbux.items;

import com.kotakotik.purpbux.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WalletMK3 extends AbstractWallet {
    @Override
    int getCapacity() {
        return 64 * 8;
    }

    @Nullable
    @Override
    public Item getPreviousWallet() {
        return ModItems.WALLETMK2.get();
    }

    @Nonnull
    @Override
    public Item getMaterial() {
        return Items.DIAMOND;
    }

    @Override
    public String getWalletName() {
        return "mk3";
    }

    @Override
    public IItemProvider getProvider() {
        return ModItems.WALLETMK3.get();
    }
}
