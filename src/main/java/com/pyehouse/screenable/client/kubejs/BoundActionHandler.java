package com.pyehouse.screenable.client.kubejs;

import com.pyehouse.screenable.kubejs.ScreenableJS;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;

public class BoundActionHandler {
    public static void registerKeyBinds(final RegisterKeyMappingsEvent event) {
        for (ScreenableJS.ClientJS.Binding binding : ScreenableJS.ClientJS.bindings) {
            KeyMapping mapping = new KeyMapping(binding.langKey(), binding.keyConflictContext(), binding.keyModifier(),
                    binding.keyCode(), binding.category());
            ScreenableJS.ClientJS.boundMappings.putIfAbsent(mapping, binding);
            event.register(mapping);
        }
    }

    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            for (KeyMapping mapping : ScreenableJS.ClientJS.boundMappings.keySet()) {
                if (mapping.consumeClick()) {
                    while(mapping.consumeClick()); // clear all clicks out but don't necessarily release
                    ScreenableJS.ClientJS.Binding binding = ScreenableJS.ClientJS.boundMappings.get(mapping);
                    if (binding != null) {
                        Runnable boundAction = ScreenableJS.ClientJS.boundActions.get(binding);
                        if (boundAction != null) {
                            boundAction.run();
                        }
                    }
                }
            }
        }
    }
}
