package com.pyehouse.screenable.common.network;

import com.pyehouse.screenable.Screenable;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public class C2SRunActionMessage {
    private boolean messageValid;
    private String screenId;
    private String componentId;
    private UUID playerUUID;

    public boolean isMessageValid() { return messageValid; }
    public String getScreenId() { return screenId; }
    public String getComponentId() { return componentId; }
    public UUID getPlayerUUID() { return playerUUID; }

    public C2SRunActionMessage() {
        messageValid = false;
    }

    public C2SRunActionMessage(String screenId, String componentId, UUID playerUUID) {
        this.screenId = screenId;
        this.componentId = componentId;
        this.playerUUID = playerUUID;
        this.messageValid = true;
    }

    public static C2SRunActionMessage decode(FriendlyByteBuf buf) {
        C2SRunActionMessage retval = new C2SRunActionMessage();
        try {
            retval.screenId = buf.readUtf();
            boolean componentIdIsNull = buf.readBoolean();
            if (!componentIdIsNull) {
                retval.componentId = buf.readUtf();
            }
            retval.playerUUID = buf.readUUID();

            retval.messageValid = true;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            Screenable.logError("Exception while reading C2SRunActionMessage");
        }
        return retval;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!messageValid) return;

        buf.writeUtf(screenId);
        buf.writeBoolean(componentId == null);
        if (componentId != null) {
            buf.writeUtf(componentId);
        }
        buf.writeUUID(playerUUID);
    }

}
