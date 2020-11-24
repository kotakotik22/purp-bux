package com.kotakotik.purpbux.messages;

import io.netty.util.AttributeKey;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BuxStationProgressUpdate extends ModMessage {
    private static int progress;
    private static int totalProgress;

    static int getId() {
        return 0;
    }

    public static int getProgress() {
        return progress;
    }

    public static int getTotalProgress() {
        return totalProgress;
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            progress = (int) ctx.get().attr(AttributeKey.newInstance("progress")).get();
            totalProgress = (int) ctx.get().attr(AttributeKey.newInstance("totalProgress")).get();
        });

        return true;
    }
}
