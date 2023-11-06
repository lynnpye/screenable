package com.pyehouse.screenable.common.network;

import com.pyehouse.screenable.Screenable;
import com.pyehouse.screenable.common.screendef.ScreenDef;
import net.minecraft.network.FriendlyByteBuf;

public class S2CScreenDisplayMessage {
    private boolean messageValid;
    private ScreenDef screenDef;

    public boolean isMessageValid() { return messageValid; }
    public ScreenDef getScreenDef() { return screenDef; }

    public S2CScreenDisplayMessage() {
        messageValid = false;
    }

    public S2CScreenDisplayMessage(ScreenDef screenDef) {
        this.screenDef = screenDef;
        this.messageValid = true;
    }

    public static S2CScreenDisplayMessage decode(FriendlyByteBuf buf) {
        S2CScreenDisplayMessage retval = new S2CScreenDisplayMessage();
        try {
            retval.screenDef = ScreenDef.fromJson(buf.readUtf());

            retval.messageValid = true;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            Screenable.logError("Exception while reading S2CScreenDisplayMessage: " + e);
        }
        return retval;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!messageValid) return;

        buf.writeUtf(this.screenDef.toJson());
    }
}
