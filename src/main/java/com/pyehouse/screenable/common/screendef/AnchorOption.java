package com.pyehouse.screenable.common.screendef;

public enum AnchorOption {
    TOP_LEFT,
    TOP_MIDDLE,
    TOP_RIGHT,
    MIDDLE_LEFT,
    MIDDLE_MIDDLE,
    MIDDLE_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_MIDDLE,
    BOTTOM_RIGHT;

    public static AnchorOption of(Object val) {
        if (val == null) return null;

        if (val instanceof AnchorOption) return (AnchorOption) val;

        try {
            return AnchorOption.valueOf(val.toString().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
