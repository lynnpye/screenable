package com.pyehouse.screenable.common.screendef;

import com.pyehouse.screenable.Screenable;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraftforge.server.ServerLifecycleHooks;

public class ScreenDefBuilder {
    protected final ScreenDef screenDef;
    private ComponentDef componentDef;

    protected ScreenDefBuilder(ScreenDef screenDef) {
        this.screenDef = screenDef;
    }

    //
    // ScreenDef attributes
    //
    @Info("Optional: In single player, should displaying this screen pause activity")
    public ScreenDefBuilder pause(boolean pause) {
        this.screenDef.pause = pause;
        return this;
    }

    @Info("Optional: Not typically used, provides a custom title for the screen (which is not normally even displayed) (purely for satisfying a constructor)")
    public ScreenDefBuilder title(String title) {
        this.screenDef.title = title;
        return this;
    }

    @Info("Optional: Provides a default anchor position. Defaults to TOP_LEFT")
    public ScreenDefBuilder defaultAnchor(AnchorOption defaultAnchor) {
        this.screenDef.defaultAnchor = defaultAnchor;
        return this;
    }

    @Info("Optional: Set whether to default to running commands in the player context by default")
    public ScreenDefBuilder defaultPreferCommandStack(CommandStackOption defaultPreferCommandStack) {
        this.screenDef.defaultPreferCommandStack = defaultPreferCommandStack;
        return this;
    }

    @Info("Optional: An action to perform any time the screen is closed, whether through pressing Escape or interacting with a component")
    public ScreenDefBuilder closingCallback(ScreenableComponentCallback closingCallback) {
        this.screenDef.closingCallback = closingCallback;
        return this;
    }

    @Info("Optional: Whether to automatically close the screen whenever a component is clicked/interacted with")
    public ScreenDefBuilder closeOnInteract(boolean closeOnInteract) {
        this.screenDef.closeOnInteract = closeOnInteract;
        return this;
    }

    //
    // ComponentDef attributes
    //
    @Info("Begin defining a new component. If a previous component was being defined, it is no longer available for modification. The id must be unique among all components for the current screen")
    public ScreenDefBuilder component(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ComponentDef.id cannot be null or blank");
        }
        for (ComponentDef def : screenDef.components) {
            if (id.equals(def.id)) {
                throw new IllegalArgumentException(String.format("ComponentDef id [%s] already exists for ScreenDef id [%s]", id, screenDef.id));
            }
        }
        this.componentDef = new ComponentDef();
        this.componentDef.id = id;
        this.screenDef.components.add(this.componentDef);
        return this;
    }

    @Info("Required: Indicates the type of component")
    public ScreenDefBuilder type(ModComponentType type) {
        this.componentDef.type = type;
        return this;
    }

    @Info("Required: The x coordinate of the component")
    public ScreenDefBuilder x(int x) {
        this.componentDef.ax = x;
        return this;
    }

    @Info("Required: The y coordinate of the component")
    public ScreenDefBuilder y(int y) {
        this.componentDef.ay = y;
        return this;
    }

    @Info("Required: The width of the component")
    public ScreenDefBuilder width(int width) {
        this.componentDef.width = width;
        return this;
    }

    @Info("Required: The height of the component")
    public ScreenDefBuilder height(int height) {
        this.componentDef.height = height;
        return this;
    }

    @Info("Required: The action to perform when the component is clicked")
    public ScreenDefBuilder callback(ScreenableComponentCallback callback) {
        this.componentDef.callback = callback;
        return this;
    }

    @Info("Required (or langKey): The text to display. Also displayed when langKey is specified but the key provided cannot be localized")
    public ScreenDefBuilder text(String text) {
        this.componentDef.text = text;
        return this;
    }

    @Info("Required (or text): The lang key to localize. Falls back to the text value if the localization cannot be found")
    public ScreenDefBuilder langKey(String langKey) {
        this.componentDef.langKey = langKey;
        return this;
    }

    @Info("Optional: Defaults to TOP_LEFT or the defaultAnchor if defined. Specifies which part of the screen the component will be positioned relative to")
    public ScreenDefBuilder anchor(AnchorOption anchor) {
        this.componentDef.anchor = anchor;
        return this;
    }

    @Info("Optional: Indicates the preferred command stack the action should be run in")
    public ScreenDefBuilder preferCommandStack(CommandStackOption preferCommandStack) {
        this.componentDef.preferCommandStack = preferCommandStack;
        return this;
    }
}
