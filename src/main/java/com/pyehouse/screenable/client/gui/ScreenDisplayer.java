package com.pyehouse.screenable.client.gui;

import com.pyehouse.screenable.common.network.S2CScreenDisplayMessage;
import net.minecraft.client.Minecraft;

public class ScreenDisplayer {
    public static void displayScreen(S2CScreenDisplayMessage message) {
        Minecraft.getInstance().setScreen(new ModScreen(message.getScreenDef()));
    }
}
