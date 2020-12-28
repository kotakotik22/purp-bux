package com.kotakotik.purpbux.commands;

import com.kotakotik.purpbux.Config;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigCommand implements Command<CommandSource> {
    // im really confused about how commands work so im just writing really weird stuff

    private static final ConfigCommand CMD = new ConfigCommand();

    public static final ConfigPart[] Configs = new ConfigPart[]{
            new ConfigPart("buxStationSecondsRequired", ConfigPart.ConfigType.NUMBER, Config.BUX_STATION_SECONDS_REQUIRED),
            new ConfigPart("expExtractorExpRequired", ConfigPart.ConfigType.NUMBER, Config.EXP_EXTRACTOR_EXP_REQUIRED),
            new ConfigPart("expBottleExpObtained", ConfigPart.ConfigType.NUMBER, Config.EXP_BOTTLE_EXP_OBTAINED)
            // do i really have to put so much info?
    };

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> command = Commands.literal("config");
        for (ConfigPart configPart : Configs) {
            String name = configPart.name;
            ConfigPart.ConfigType type = configPart.type;
            ForgeConfigSpec.ConfigValue value = configPart.value;
            ArgumentType commandType;
            switch (type) {
                case NUMBER:
                    commandType = IntegerArgumentType.integer();
                    break;
                case BOOLEAN:
                    commandType = BoolArgumentType.bool();
                    break;
                default:
                    commandType = StringArgumentType.string();
            }
            command.then(Commands.literal(name)
                    .then(Commands.argument("value", commandType).executes(context -> {
                        Object val;
                        System.out.println("heldssssdrgdfhdthtdhf5ejrjilobatr.kuihtrjhunsgh");
                        // i know im kinda using the same switch here but i dint know how not to do that
                        switch (type) {
                            case NUMBER:
                                val = IntegerArgumentType.getInteger(context, "value");
                                break;
                            case BOOLEAN:
                                val = BoolArgumentType.getBool(context, "value");
                                break;
                            default:
                                val = StringArgumentType.getString(context, "value");
                        }
                        value.set(val);
                        return 1;
                    })));
        }
        command.requires(cs -> cs.hasPermissionLevel(2));
        return command;
    }

    @Override
    public int run(CommandContext<CommandSource> context) {
        // idk what to do here
        // too lazy to delete this part
        return 1;
    }

    public static class ConfigPart {
        public enum ConfigType {
            NUMBER,
            STRING,
            BOOLEAN
        }

        public String name;
        public ConfigType type;
        public ForgeConfigSpec.ConfigValue value;

        public ConfigPart(String name, ConfigType type, ForgeConfigSpec.ConfigValue value) {
            this.type = type;
            this.name = name;
            this.value = value;
        }
    }
}