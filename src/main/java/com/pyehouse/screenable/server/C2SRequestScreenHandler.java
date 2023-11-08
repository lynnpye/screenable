package com.pyehouse.screenable.server;

import com.pyehouse.screenable.Screenable;
import com.pyehouse.screenable.common.Config;
import com.pyehouse.screenable.common.network.C2SRequestScreenMessage;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SRequestScreenHandler {
    public static void onRequestScreen(final C2SRequestScreenMessage message, final Supplier<NetworkEvent.Context> ctxSupplier) {
        ctxSupplier.get().enqueueWork(() -> {
            C2SRequestScreenHandler.requestScreen(message, ctxSupplier.get().getSender());
        });
        ctxSupplier.get().setPacketHandled(true);
    }

    private static void requestScreen(final C2SRequestScreenMessage message, final Player player) {
        Screenable.requestClientScreenDisplay(Config.getScreenDef(message.getScreenId()), player);
    }
}
