package com.pyehouse.screenable.kubejs;

import com.mojang.blaze3d.platform.InputConstants;
import com.pyehouse.screenable.Screenable;
import com.pyehouse.screenable.common.Config;
import com.pyehouse.screenable.common.screendef.ScreenDef;
import com.pyehouse.screenable.common.screendef.ScreenDefBuilder;
import com.pyehouse.screenable.common.screendef.ScreenDefBuilderHelper;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ScreenableJS {
    public static class ServerJS {
        @Info("Returns true if the id refers to a valid screen, false otherwise")
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
            Screenable.requestClientScreenDisplay(Config.getScreenDef(screenId), player);
        }
    }

    public static class ClientJS {
        public static final Set<Binding> bindings = new HashSet<>();
        public static final Map<KeyMapping, Binding> boundMappings = new HashMap<>();
        public static final Map<Binding, Runnable> boundActions = new HashMap<>();

        @Info("Ask server to send the indicated screen to the requesting client. Requires player to be OP, or the screen to be defined to allow client requests.")
        public static void requestScreen(String screenId) {
            Screenable.requestScreenForClient(screenId);
        }

        public static void bindAction(Binding binding, Runnable boundAction) {
            bindings.add(binding);
            boundActions.putIfAbsent(binding, boundAction);
        }

        public static void clearBindings() {
            boundMappings.clear();
            bindings.clear();
            boundActions.clear();
        }

        public record Binding(String langKey, IKeyConflictContext keyConflictContext, KeyModifier keyModifier,
                              InputConstants.Key keyCode, String category) {
            public Binding {}

            public Binding(String langKey, int keyCode, String category) {
                this(langKey, KeyConflictContext.UNIVERSAL, KeyModifier.NONE, InputConstants.Type.KEYSYM.getOrCreate(keyCode), category);
            }

            public Binding(String langKey, KeyModifier keyModifier, int keyCode, String category) {
                this(langKey, KeyConflictContext.UNIVERSAL, keyModifier, InputConstants.Type.KEYSYM.getOrCreate(keyCode), category);
            }
        }
    }
}
