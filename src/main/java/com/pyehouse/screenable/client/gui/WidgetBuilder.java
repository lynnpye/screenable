package com.pyehouse.screenable.client.gui;

import com.pyehouse.screenable.common.screendef.ComponentDef;
import com.pyehouse.screenable.common.screendef.ScreenDef;
import net.minecraft.client.gui.components.AbstractWidget;

public class WidgetBuilder {
    public static AbstractWidget fromComponentDef(ModScreen modScreen, ScreenDef screenDef, ComponentDef componentDef) {
        if (componentDef == null || componentDef.id == null) {
            return null;
        }

        switch (componentDef.type) {
            case BUTTON: {
                return new ModButton(
                        modScreen,
                        screenDef,
                        componentDef
                );
            }
        }

        return null;
    }
}
