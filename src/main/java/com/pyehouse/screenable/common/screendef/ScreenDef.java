package com.pyehouse.screenable.common.screendef;

import com.google.gson.Gson;
import com.pyehouse.screenable.Screenable;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ScreenDef {
    // Required
    // A unique identifier for all ScreenDef definitions
    // Duplicate identifiers mean that one or more ScreenDefs will be ignored
    public String id;
    // Optional
    // Determines whether the screen should pause activity in the world. Only matters in single player.
    // Defaults to false
    public boolean pause;
    // Optional
    // Title for the screen. Not usually displayed.
    public String title;
    // Optional
    // Valid values:
    //      TOP_LEFT, TOP_MIDDLE, TOP_RIGHT
    //      MIDDLE_LEFT, MIDDLE_MIDDLE, MIDDLE_RIGHT
    //      BOTTOM_LEFT, BOTTOM_MIDDLE, BOTTOM_RIGHT
    // Default anchor. If set, and a child ComponentDef lacks an anchor, the ScreenDef anchor will be used.
    // Used to determine what the coordinates are relative to.
    public AnchorOption defaultAnchor;
    // Optional
    // Valid values:
    //      PLAYER
    //      SERVER
    // Only the value 'player' currently matters. If this value is set to 'player', the command context will
    // be executed as the player, allowing use of, for example, @s target selectors.
    // If an '@s' is detected in the command string, 'player' context will be assumed.
    public CommandStackOption defaultPreferCommandStack;
    // Optional
    // A command to be run when the screen is closed, whether by hitting escape or by clicking a widget while
    // closeOnInteract is true.
    public transient ScreenableComponentCallback closingCallback;
    // Optional
    // If true, the first widget the player interacts with will also trigger the screen to close.
    public boolean closeOnInteract;
    // Required
    // I mean, technically not required, but won't do much without some components.
    public List<ComponentDef> components = new ArrayList<>();

    public ScreenDef() {
    }

    public String toJson() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

    public static ScreenDef fromJson(String json) {
        return new Gson().fromJson(json, ScreenDef.class);
    }

    /**
     * Is this ScreenDef valid, that is, are all required values set and are all values with defined ranges within range?
     * @return true if valid
     */
    public boolean isValid() {
        if (id == null || id.isBlank()) {
            Screenable.logError("ScreenDef invalid: missing id or is blank");
            return false;
        }
        // must have at least one component
        if (components.isEmpty()) {
            Screenable.logError("ScreenDef id[%s] has no components defined", id);
            return false;
        }
        for (ComponentDef componentDef : this.components) {
            if (!componentDef.isValid()) {
                return false;
            }
        }

        return true;
    }

    public Component getTitleComponentOrDefault() {
        return Component.literal(title == null || title.isBlank() ? Screenable.MODID : title);
    }
}
