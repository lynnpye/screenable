package com.pyehouse.screenable.common.network;

import com.pyehouse.screenable.Screenable;
import net.minecraft.network.FriendlyByteBuf;

public class C2SRequestScreenMessage {
    private boolean messageValid;
    private String screenId;

    public boolean isMessageValid() {
        return messageValid;
    }
    public String getScreenId() {
        return screenId;
    }

    public C2SRequestScreenMessage() {
        messageValid = false;
    }

    public C2SRequestScreenMessage(String screenId) {
        this.screenId = screenId;
        this.messageValid = true;
    }

    public static C2SRequestScreenMessage decode(FriendlyByteBuf buf) {
        C2SRequestScreenMessage retval = new C2SRequestScreenMessage();
        try {
            retval.screenId = buf.readUtf();
            retval.messageValid = true;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            Screenable.logError("Exception while reading C2SRequestScreenMessage");
        }
        return retval;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!messageValid) return;

        buf.writeUtf(screenId);
    }
}
