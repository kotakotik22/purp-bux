package com.kotakotik.purpbux.items;

import com.kotakotik.purpbux.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WalletMK4 extends AbstractWallet {
    @Override
    int getWalletnum() {
        return 4;
    }

    @Nullable
    @Override
    public Item getPreviousWallet() {
        return ModItems.WALLETMK3.get();
    }

    @Nonnull
    @Override
    public Item getMaterial() {
        return Items.EMERALD_BLOCK;
    }

    @Override
    public String getWalletName() {
        return "mk4";
    }

    @Override
    public IItemProvider getProvider() {
        return ModItems.WALLETMK4.get();
    }
}
