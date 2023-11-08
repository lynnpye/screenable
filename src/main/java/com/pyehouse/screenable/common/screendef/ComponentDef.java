package com.pyehouse.screenable.common.screendef;

import com.pyehouse.screenable.Screenable;
import net.minecraft.network.chat.Component;

public class ComponentDef {
    // Required
    // Unique identifier among all ComponentDefs contained by a ScreenDef. Can be duplicated by a
    // ComponentDef defined in a different ScreenDef
    public String id;
    // Required
    // Valid values:
    //      BUTTON
    // Determines what type of widget this is.
    public ModComponentType type;
    // Required
    // The horizontal offset from the anchor. For _LEFT and _MIDDLE anchors, it pushes to the right.
    // For _RIGHT anchors it pushes to the left.
    public int ax;
    // Required
    // The vertical offset from the anchor. For TOP_ and MIDDLE_ anchors, it pushes down.
    // For BOTTOM_ anchors it pushes up.
    public int ay;
    // Required
    // The width of the widget
    public int width;
    // Required
    // The height of the widget
    public int height;
    // Required
    // The command to run on the server in response to interaction with the widget
    public transient ScreenableComponentCallback callback;
    // Required or langKey
    // Raw text to display on the widget. This is not localized and will be used as a fallback if langKey is specified
    // but the localization cannot be found.
    public String text;
    // Required or text
    // A translation key to use to present the text of the widget. If localization lookup fails, falls back to text.
    public String langKey;
    // Optional
    // Valid values:
    //      TOP_LEFT, TOP_MIDDLE, TOP_RIGHT
    //      MIDDLE_LEFT, MIDDLE_MIDDLE, MIDDLE_RIGHT
    //      BOTTOM_LEFT, BOTTOM_MIDDLE, BOTTOM_RIGHT
    // Used to determine what the coordinates are relative to.
    public AnchorOption anchor;

    public ComponentDef() {
    }

    public boolean isValid() {
        if (id == null || id.isBlank()) {
            Screenable.logError("ComponentDef invalid: missing id or is blank");
            return false;
        }
        if (type == null) {
            Screenable.logError("ComponentDef id[%s] requires type", id);
            return false;
        }
        if (width < 1 && type != ModComponentType.LABEL) {
            Screenable.logError("ComponentDef id[%s] width must be a positive integer (but with type LABEL, setting width to 0 allows unlimited width)", id);
            return false;
        }
        if (height < 1) {
            Screenable.logError("ComponentDef id[%s] height must be a positive integer", id);
            return false;
        }
        if (type.isCallbackRequired() && callback == null) {
            Screenable.logError("ComponentDef id[%s] with type[%s] requires a callback", id, type);
            return false;
        }
        if ((text == null || text.isBlank()) && (langKey == null || langKey.isBlank())) {
            Screenable.logError("ComponentDef id[%s] cannot have both text and langKey null or blank");
            return false;
        }

        return true;
    }

    public Component getDisplayStringComponent() {
        Component result = null;
        if (langKey != null) {
            result = Component.translatable(langKey);
            if (langKey.equals(result.getString()) && text != null) {
                result = null;
            }
        }
        if (result == null) {
            result = Component.literal(text == null ? "" : text);
        }
        return result;
    }
}
