package com.kotakotik.purpbux;

import com.kotakotik.purpbux.messages.BuxStationProgressUpdate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ModPacketHandler {
    public static int ID = 0;
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Purpbux.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int nextID() {
        return ID++;
    }

    public static void register() {
//        INSTANCE.registerMessage(BuxStationProgressUpdate.class, nextID());
        INSTANCE.messageBuilder(BuxStationProgressUpdate.class, nextID())
                .encoder((BuxStationProgressUpdate, packetBuffer) -> {})
                .decoder(buf -> new BuxStationProgressUpdate())
                .consumer(BuxStationProgressUpdate::handle)
                .add();
    }

    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
