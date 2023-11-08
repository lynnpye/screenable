package com.pyehouse.screenable.client.gui;

import com.pyehouse.screenable.Screenable;
import com.pyehouse.screenable.common.screendef.ComponentDef;
import com.pyehouse.screenable.common.screendef.ScreenDef;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class ModButton extends ExtendedButton {
    public ModButton(ModScreen modScreen, ScreenDef screenDef, ComponentDef componentDef, int reanchoredX, int reanchoredY) {
        super(reanchoredX, reanchoredY, componentDef.width, componentDef.height, componentDef.getDisplayStringComponent(), self -> {
            Screenable.requestServerRunAction(screenDef.id, componentDef.id, Minecraft.getInstance().player.getUUID());
            if (screenDef.closeOnInteract) {
                modScreen.onClose();
            }
        });
    }
}
