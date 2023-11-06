package com.pyehouse.screenable.kubejs;

import com.pyehouse.screenable.Screenable;
import com.pyehouse.screenable.common.Config;
import com.pyehouse.screenable.common.screendef.ScreenDef;
import com.pyehouse.screenable.common.screendef.ScreenDefBuilder;
import com.pyehouse.screenable.common.screendef.ScreenDefBuilderHelper;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.world.entity.player.Player;

public class ScreenableJS {
    public static final String JSNAME = "Screenable";

    public static boolean isScreenValid(String id){
        if (id == null) {
            throw new IllegalArgumentException("isScreenValid: ScreenDef id cannot be null");
        }
        ScreenDef screen = Config.screens.get(id);
        if (screen == null) {
            return false;
        }
        return screen.isValid();
    }

    @Info("Removes the cached screen definition, using the provided id as the key")
    public static void forgetScreen(String id) {
        Config.forgetScreenDef(id);
    }

    @Info("Creates a new instance of a ScreenDefBuilder using the provided id as the key for the new ScreenDef")
    public static ScreenDefBuilder screen(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ScreenDef id cannot be null or blank");
        }
        ScreenDef screenDef = new ScreenDef();
        screenDef.id = id;
        ScreenDefBuilder builder = ScreenDefBuilderHelper.newBuilder(screenDef);
        Config.putScreenDef(screenDef);
        return builder;
    }

    @Info("Request the indicated screen be displayed to the target player")
    public static void showScreen(String screenId, Player player) {
        if (!isScreenValid(screenId)) {
            throw new IllegalArgumentException("showScreen: screenId does not refer to a valid screen");
        }
        // okay, now do something
    }
}
