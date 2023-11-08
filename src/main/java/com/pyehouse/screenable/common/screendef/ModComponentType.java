package com.pyehouse.screenable.common.screendef;

public enum ModComponentType {
    LABEL(false),
    BUTTON(true);

    private final boolean callbackRequired;

    private ModComponentType(boolean callbackRequired) {
        this.callbackRequired = callbackRequired;
    }

    public boolean isCallbackRequired() {
        return callbackRequired;
    }

    public static ModComponentType of (Object val) {
        if (val == null) return null;

        if (val instanceof ModComponentType) return (ModComponentType) val;

        try {
            return ModComponentType.valueOf(val.toString().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
