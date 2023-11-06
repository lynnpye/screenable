package com.pyehouse.screenable.kubejs;

import com.pyehouse.screenable.common.Config;
import com.pyehouse.screenable.common.screendef.AnchorOption;
import com.pyehouse.screenable.common.screendef.CommandStackOption;
import com.pyehouse.screenable.common.screendef.ModComponentType;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class ScreenablePlugin extends KubeJSPlugin {
    @Override
    public void registerBindings(BindingsEvent event) {
        if (event.getType().isServer()) {
            event.add(ScreenableJS.JSNAME, ScreenableJS.class);
            event.add(AnchorOption.JSNAME, AnchorOption.class);
            event.add(CommandStackOption.JSNAME, CommandStackOption.class);
            event.add(ModComponentType.JSNAME, ModComponentType.class);
        }
    }

    @Override
    public void clearCaches() {
        Config.resetScreens();
    }
}
