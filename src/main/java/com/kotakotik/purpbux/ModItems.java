package com.kotakotik.purpbux;

import com.kotakotik.purpbux.items.*;
import com.kotakotik.purpbux.translation.TranslationKeys;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;

public class ModItems {
    public static final RegistryObject<Item> PURP_BUX = Registration.ITEMS.register("purp_bux", () ->
            new Item(new Item.Properties().group(Purpbux.TAB)) {
                @Override
                public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flagIn) {
                    list.add(TranslationKeys.TOOLTIP_PURPBUX.getComponent());
                }
            });

    public static final RegistryObject<Item> WALLET = Registration.ITEMS.register("wallet", BasicWallet::new);
    public static final RegistryObject<Item> WALLETMK2 = Registration.ITEMS.register("walletmk2", WalletMK2::new);
    public static final RegistryObject<Item> WALLETMK3 = Registration.ITEMS.register("walletmk3", WalletMK3::new);
    public static final RegistryObject<Item> WALLETMK4 = Registration.ITEMS.register("walletmk4", WalletMK4::new);

    public static final RegistryObject<Item> EXP_BOTTLE = Registration.ITEMS.register("exp_bottle", () ->
            new ExpBottle(new Item.Properties().group(Purpbux.TAB).food(new Food.Builder()
                    .hunger(0)
                    .saturation(0)
                    .setAlwaysEdible()
                    .build())));

    static void register() {
    }
}
