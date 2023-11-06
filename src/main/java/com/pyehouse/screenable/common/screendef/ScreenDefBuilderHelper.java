package com.pyehouse.screenable.common.screendef;

public class ScreenDefBuilderHelper {
    public static ScreenDefBuilder newBuilder(ScreenDef screenDef) {
        return new ScreenDefBuilder(screenDef);
    }
}
