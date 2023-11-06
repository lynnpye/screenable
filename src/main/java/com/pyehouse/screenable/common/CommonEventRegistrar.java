package com.pyehouse.screenable.common;

import com.pyehouse.screenable.AbstractEventRegistrar;
import com.pyehouse.screenable.common.command.ModCommands;
import com.pyehouse.screenable.common.network.NetworkConfig;
import net.minecraftforge.eventbus.api.IEventBus;

public class CommonEventRegistrar extends AbstractEventRegistrar {
    public CommonEventRegistrar(IEventBus modEventBus, IEventBus forgeEventBus) {
        super(modEventBus, forgeEventBus);
    }

    @Override
    public void registration() {
        modEventBus.register(NetworkConfig.class);

        forgeEventBus.register(ModCommands.class);
    }
}
