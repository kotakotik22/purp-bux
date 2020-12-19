package com.kotakotik.purpbux.items;

public class Wallets {
    public static final Class<?>[] wallets = new Class<?>[]{
            BasicWallet.class,
            WalletMK2.class,
            WalletMK3.class,
            WalletMK4.class
    };

    public static final Class<BasicWallet> basicWallet = BasicWallet.class;
    public static final Class<WalletMK2> walletMK2 = WalletMK2.class;
    public static final Class<WalletMK3> walletMK3 = WalletMK3.class;
    public static final Class<WalletMK4> walletMK4 = WalletMK4.class;
}
