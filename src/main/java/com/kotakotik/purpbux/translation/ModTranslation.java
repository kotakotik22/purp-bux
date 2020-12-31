package com.kotakotik.purpbux.translation;

import net.minecraft.util.text.TranslationTextComponent;

public class ModTranslation {
    public static final String getTranslation(String key, String... args) {
        return new TranslationTextComponent(key, args).getString();
    }
}
