package com.pyehouse.screenable.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class ModLabel extends GuiComponent implements Widget, NarratableEntry {

    private final Component message;
    private int x;
    private int y;
    private int width;
    private int height;
    private transient int widthProxy;

    //private boolean isHovered;

    public ModLabel(Component message, int x, int y, int width, int height) {
        this.message = message;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float deltaFrameTime) {
        boolean unlimitedWidth = this.width < 1;
        widthProxy = widthProxy > 0 ? widthProxy : (this.width > 0 ? this.width : Minecraft.getInstance().font.width(this.message));
        //this.isHovered = mouseX > this.x && mouseX < this.x + widthProxy && mouseY > this.y && mouseY < this.y + this.height;
        if (!unlimitedWidth) {
            GuiComponent.enableScissor(this.x + 1, this.y + 1, this.x + widthProxy - 1, this.y + this.height - 1);
        }
        drawCenteredString(poseStack, Minecraft.getInstance().font, this.message, this.x + widthProxy / 2,
                this.y + (this.height - 8) / 2, Color.white.getRGB());
        if (!unlimitedWidth) {
            GuiComponent.disableScissor();
        }
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.HOVERED;
    }

    @Override
    public boolean isActive() {
        return NarratableEntry.super.isActive();
    }

    @Override
    public void updateNarration(NarrationElementOutput output) {
        output.add(NarratedElementType.HINT, this.message);
    }
}
