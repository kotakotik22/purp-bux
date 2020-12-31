package com.kotakotik.purpbux;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.HashMap;

@Mod.EventBusSubscriber
public class Config {
    public static final String CATEGORY_GENERAL = "general";

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.IntValue BUX_STATION_SECONDS_REQUIRED;
    public static ForgeConfigSpec.IntValue EXP_EXTRACTOR_EXP_REQUIRED;
    public static ForgeConfigSpec.IntValue EXP_BOTTLE_EXP_OBTAINED;
    public static ForgeConfigSpec.IntValue BUX_STATION_MAX_STACK;
    public static ForgeConfigSpec.IntValue WALLET_CAPACITY_NUMBER_A;
    public static ForgeConfigSpec.IntValue WALLET_CAPACITY_NUMBER_B;

    public static class Conf {
        public final String comment;
        public final Object default1;

        public Conf(String comment, int default1) {
            this.comment = comment;
            this.default1 = default1;
        }
    }

    public static HashMap<String, Conf> confInfo = new HashMap<>();

    private static ForgeConfigSpec.IntValue createConfig(ForgeConfigSpec.Builder builder, int default1, String comment, String name, int min, int max) {
        confInfo.put(name, new Conf(comment, default1));

        return builder.comment(comment)
                .comment("default: " + default1)
                .defineInRange(name, default1, min, max);
    }

    private static ForgeConfigSpec.IntValue createConfig(ForgeConfigSpec.Builder builder, int default1, String comment, String name) {
        return createConfig(builder, default1, comment, name, 0, Integer.MAX_VALUE);
    }

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

        BUX_STATION_SECONDS_REQUIRED = createConfig(SERVER_BUILDER,
                5,
                "Seconds to make a Purp bux in the Bux station",
                "buxStationSecondsRequired"
        );

        EXP_EXTRACTOR_EXP_REQUIRED = createConfig(SERVER_BUILDER,
                50,
                "Experience points required to create bottled experience in the Experience extractor",
                "expExtractorExpRequired");

        EXP_BOTTLE_EXP_OBTAINED = createConfig(SERVER_BUILDER,
                20,
                "Experience obtained from drinking bottled experience - recommended to be less than the required exp to create",
                "expBottleExpObtained"
        );

        BUX_STATION_MAX_STACK = createConfig(SERVER_BUILDER,
                64,
                "Maximum amount of items that can be stored in a Purpbux slot. Warning: Very glitchy!",
                "buxStationMaxStack"
        );

        WALLET_CAPACITY_NUMBER_A = createConfig(SERVER_BUILDER,
                64,
                "The number A used to determine the capacity of a wallet: A * (B ^ walletnum)",
                "walletCapacityNumberA"
        );

        WALLET_CAPACITY_NUMBER_B = createConfig(SERVER_BUILDER,
                2,
                "The number B used to determine the capacity of a wallet: A * (B ^ walletnum)",
                "walletCapacityNumberB"
        );

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
