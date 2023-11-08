package com.pyehouse.screenable.client.gui;

import com.pyehouse.screenable.common.screendef.AnchorOption;
import com.pyehouse.screenable.common.screendef.ComponentDef;
import com.pyehouse.screenable.common.screendef.ScreenDef;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class WidgetBuilder {
    public static void fromComponentDef(ModScreen modScreen, ScreenDef screenDef, ComponentDef componentDef) {
        if (modScreen == null || screenDef == null || componentDef == null || componentDef.id == null) {
            return;
        }

        int reanchoredX = componentDef.ax;
        int reanchoredY = componentDef.ay;
        Component componentDefDisplayString = componentDef.getDisplayStringComponent();

        AnchorOption anchor = componentDef.anchor != null ? componentDef.anchor : screenDef.defaultAnchor;
        if (anchor != null) {
            int screenWidthOffset = 0;
            int virtualScreenWidth = modScreen.width;
            if (screenDef.screenWidth > 0) {
                screenWidthOffset = (modScreen.width - screenDef.screenWidth) / 2;
                virtualScreenWidth = screenDef.screenWidth;
            }
            switch (anchor) {
                case MIDDLE_LEFT:
                case MIDDLE_MIDDLE:
                case MIDDLE_RIGHT:
                    reanchoredY = (modScreen.height / 2) - (componentDef.height / 2) + componentDef.ay;
                    break;
                case BOTTOM_LEFT:
                case BOTTOM_MIDDLE:
                case BOTTOM_RIGHT:
                    reanchoredY = modScreen.height - componentDef.ay - componentDef.height;
                    break;
            }
            // LABEL preprocessing for message length
            // if label width is 0, calculate it for current font
            componentDef.width = componentDef.width > 0 ? componentDef.width : Minecraft.getInstance().font.width(componentDefDisplayString);
            switch (anchor) {
                case TOP_LEFT:
                case MIDDLE_LEFT:
                case BOTTOM_LEFT:
                    reanchoredX = screenWidthOffset + componentDef.ax;
                    break;
                case TOP_MIDDLE:
                case MIDDLE_MIDDLE:
                case BOTTOM_MIDDLE:
                    reanchoredX = (modScreen.width / 2) - (componentDef.width / 2) + componentDef.ax;
                    break;
                case TOP_RIGHT:
                case MIDDLE_RIGHT:
                case BOTTOM_RIGHT:
                    reanchoredX = screenWidthOffset + virtualScreenWidth - componentDef.width - componentDef.ax;
                    break;
            }
        }

        switch (componentDef.type) {
            case LABEL: {
                modScreen.addForRenderableOnly(new ModLabel(
                    componentDefDisplayString,
                    reanchoredX,
                    reanchoredY,
                    componentDef.width,
                    componentDef.height
                ));
            }
            break;

            case BUTTON: {
                modScreen.addForRenderableWidget(new ModButton(
                    modScreen,
                    screenDef,
                    componentDef,
                    reanchoredX,
                    reanchoredY
                ));
            }
        }
    }
}
