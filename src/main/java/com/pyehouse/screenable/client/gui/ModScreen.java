package com.pyehouse.screenable.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pyehouse.screenable.Screenable;
import com.pyehouse.screenable.common.screendef.ComponentDef;
import com.pyehouse.screenable.common.screendef.ScreenDef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;

public class ModScreen extends Screen {

    private final ScreenDef screenDef;

    public ModScreen(ScreenDef screenDef) {
        super(screenDef.getTitleComponentOrDefault());
        this.screenDef = screenDef;
    }

    @Override
    public void onClose() {
        // we can do other stuff
        Screenable.requestServerRunAction(screenDef.id, null, Minecraft.getInstance().player.getUUID());

        // but also...
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return screenDef.pause;
    }

    @Override
    protected void init() {
        super.init();

        this.buildFromScreenDef();
    }

    protected void addForRenderableOnly(Widget w) {
        this.addRenderableOnly(w);
    }

    protected void addForRenderableWidget(AbstractWidget aw) {
        this.addRenderableWidget(aw);
    }

    private void buildFromScreenDef() {
        for (ComponentDef componentDef : screenDef.components) {
            WidgetBuilder.fromComponentDef(this, screenDef, componentDef);
        }
    }
}
