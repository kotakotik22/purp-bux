package com.kotakotik.purpbux.messages;

import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

abstract public class ModMessage {
    static int getId() {
        return -1;
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        return true;
    }
}
