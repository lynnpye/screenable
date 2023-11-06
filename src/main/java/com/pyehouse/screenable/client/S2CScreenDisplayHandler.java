package com.pyehouse.screenable.client;

import com.pyehouse.screenable.client.gui.ScreenDisplayer;
import com.pyehouse.screenable.common.network.S2CScreenDisplayMessage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CScreenDisplayHandler {
    public static void onScreenDisplay(final S2CScreenDisplayMessage message, final Supplier<NetworkEvent.Context> ctxSupplier) {
        ctxSupplier.get().enqueueWork(() ->
                DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> ScreenDisplayer.displayScreen(message))
        );
    }
}
