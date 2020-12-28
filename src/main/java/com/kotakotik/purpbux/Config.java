package com.kotakotik.purpbux;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class Config {
    public static final String CATEGORY_GENERAL = "general";

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.IntValue BUX_STATION_SECONDS_REQUIRED;
    public static ForgeConfigSpec.IntValue EXP_EXTRACTOR_EXP_REQUIRED;
    public static ForgeConfigSpec.IntValue EXP_BOTTLE_EXP_OBTAINED;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

        BUX_STATION_SECONDS_REQUIRED = SERVER_BUILDER.comment("Seconds to make a Purp bux in the Bux station")
                .defineInRange("buxStationSecondsRequired", 5, 0, Integer.MAX_VALUE);

        EXP_EXTRACTOR_EXP_REQUIRED = SERVER_BUILDER.comment("Experience points required to create bottled experience in the Experience extractor")
                .defineInRange("expExtractorExpRequired", 50, 0, Integer.MAX_VALUE);

        EXP_BOTTLE_EXP_OBTAINED = SERVER_BUILDER.comment("Experience obtained from drinking bottled experience - recommended to be less than the required exp to create")
                .defineInRange("expBottleExpObtained", 20, 0, Integer.MAX_VALUE);

        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
    }
}
