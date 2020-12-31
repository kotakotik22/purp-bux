package com.kotakotik.purpbux.translation;

import net.minecraft.util.text.TranslationTextComponent;

public enum TranslationKeys {
    // JEI titles
    JEI_PURPBUX_CREATION("jei.purpbux_creation"),
    JEI_EXP_BOTTLE_CREATION("jei.exp_bottle_creation"),

    // JEI info
    JEI_EXP_BOTTLE_EXP_REQUIRED("jei.exp_bottle_exp_required"),
    JEI_PURPBUX_SECONDS_REQUIRED("jei.purpbux_seconds_required"),

    // Tooltips
    TOOLTIP_BUX_STATION("tooltip.bux_station"),

    TOOLTIP_EXP_EXTRACTOR("tooltip.exp_extractor"),

    TOOLTIP_WALLET_INFO("tooltip.wallet_info"),
    TOOLTIP_WALLET("tooltip.wallet"),
    TOOLTIP_WALLET_ADD("tooltip.wallet_add"),
    TOOLTIP_WALLET_REMOVE("tooltip.wallet_remove"),

    TOOLTIP_EXP_BOTTLE("tooltip.exp_bottle"),

    TOOLTIP_PURPBUX("tooltip.purpbux");

    public final String key;

    TranslationKeys(String key) {
        this.key = "purpbux.info." + key;
    }

    public String translate(String... args) {
        return ModTranslation.getTranslation(key, args);
    }

    public TranslationTextComponent getComponent(String... args) {
        return new TranslationTextComponent(key, args);
    }
}
