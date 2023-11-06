package com.pyehouse.screenable.client;

import com.pyehouse.screenable.AbstractEventRegistrar;
import net.minecraftforge.eventbus.api.IEventBus;

public class ClientEventRegistrar extends AbstractEventRegistrar {
    public ClientEventRegistrar(IEventBus modEventBus, IEventBus forgeEventBus) {
        super(modEventBus, forgeEventBus);
    }

    @Override
    public void registration() {

    }
}
