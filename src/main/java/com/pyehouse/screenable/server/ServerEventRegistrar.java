package com.pyehouse.screenable.server;

import com.pyehouse.screenable.AbstractEventRegistrar;
import net.minecraftforge.eventbus.api.IEventBus;

public class ServerEventRegistrar extends AbstractEventRegistrar {
    public ServerEventRegistrar(IEventBus modEventBus, IEventBus forgeEventBus) {
        super(modEventBus, forgeEventBus);
    }

    @Override
    public void registration() {

    }
}
