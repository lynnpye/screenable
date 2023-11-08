package com.pyehouse.screenable.kubejs;

import com.mojang.blaze3d.platform.InputConstants;
import com.pyehouse.screenable.common.Config;
import com.pyehouse.screenable.common.screendef.AnchorOption;
import com.pyehouse.screenable.common.screendef.ModComponentType;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;

public class ScreenablePlugin extends KubeJSPlugin {
    private static final String SCREENABLE_BINDING = "Screenable";
    private static final String ANCHOR_OPTION_BINDING = "AnchorOption";
    private static final String COMPONENT_TYPE_BINDING = "ComponentType";
    private static final String BINDING_BINDING = "Binding";
    private static final String INPUTCONSTANTS_BINDING = "InputConstants";

    @Override
    public void registerBindings(BindingsEvent event) {
        if (event.getType().isServer()) {
            event.add(SCREENABLE_BINDING, ScreenableJS.ServerJS.class);
            event.add(ANCHOR_OPTION_BINDING, AnchorOption.class);
            event.add(COMPONENT_TYPE_BINDING, ModComponentType.class);
        }
        if (event.getType().isClient()) {
            event.add(SCREENABLE_BINDING, ScreenableJS.ClientJS.class);
            event.add(BINDING_BINDING, ScreenableJS.ClientJS.Binding.class);
            event.add(INPUTCONSTANTS_BINDING, InputConstants.class);
        }
    }

    @Override
    public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        // okay... wtf
        // well, we don't have a "sided" version of a kubejs preload/prereload event
        // so registerTypeWrappers is the earliest point in the reload cycle where our
        // plugin is not only told it's happening but also which side it's on
        onPreReload(type);

        typeWrappers.registerSimple(AnchorOption.class, AnchorOption::of);
        typeWrappers.registerSimple(ModComponentType.class, ModComponentType::of);
    }

    // really ought to be an at-Override
    private void onPreReload(final ScriptType scriptType) {
        // don't clear client side bindings and trigger a reload if only the server scripts have changed
        if (scriptType.isClient()) {
            ScreenableJS.ClientJS.clearBindings();
        }
        // again, only bother with this if affecting the server side of things
        if (scriptType.isServer()) {
            Config.resetScreens();
        }
    }

}
