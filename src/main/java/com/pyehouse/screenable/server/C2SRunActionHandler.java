package com.pyehouse.screenable.server;

import com.pyehouse.screenable.common.Config;
import com.pyehouse.screenable.common.network.C2SRunActionMessage;
import com.pyehouse.screenable.common.screendef.ComponentDef;
import com.pyehouse.screenable.common.screendef.ScreenDef;
import com.pyehouse.screenable.common.screendef.ScreenableComponentCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.function.Supplier;

public class C2SRunActionHandler {
    public static void onRunAction(final C2SRunActionMessage message, final Supplier<NetworkEvent.Context> ctxSupplier) {
        ctxSupplier.get().enqueueWork(() -> {
            C2SRunActionHandler.runAction(message);
        });
        ctxSupplier.get().setPacketHandled(true);
    }

    private static void runAction(final C2SRunActionMessage message) {
        String screenId = message.getScreenId();
        if (screenId == null) {
            return;
        }
        ScreenDef screenDef = Config.getScreenDef(screenId);
        if (screenDef == null) {
            return;
        }

        String componentId = message.getComponentId();
        ScreenableComponentCallback callback = screenDef.closingCallback;

        if (componentId != null) {
            for (ComponentDef componentDef : screenDef.components) {
                if (!componentId.equals(componentDef.id)) {
                    continue;
                }
                callback = componentDef.callback;
                break;
            }
        }

        if (callback != null) {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            Player player = server.getPlayerList().getPlayer(message.getPlayerUUID());
            callback.doCallback(player);
        }
    }
}
