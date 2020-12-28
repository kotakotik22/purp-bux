package com.kotakotik.purpbux.items;

import com.kotakotik.purpbux.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;

import javax.annotation.Nonnull;

public class WalletMK2 extends AbstractWallet {
    @Override
    int getWalletnum() {
        return 2;
    }

    @Override
    public Item getPreviousWallet() {
        return ModItems.WALLET.get();
    }

    @Nonnull
    @Override
    public Item getMaterial() {
        return Items.IRON_INGOT;
    }

    @Override
    public String getWalletName() {
        return "mk2";
    }

    @Override
    public IItemProvider getProvider() {
        return ModItems.WALLETMK2.get();
    }
}
