package com.pyehouse.screenable.client;

import com.pyehouse.screenable.AbstractEventRegistrar;
import com.pyehouse.screenable.Screenable;
import com.pyehouse.screenable.client.kubejs.BoundActionHandler;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

public class ClientEventRegistrar extends AbstractEventRegistrar {
    public ClientEventRegistrar(IEventBus modEventBus, IEventBus forgeEventBus) {
        super(modEventBus, forgeEventBus);
    }

    @Override
    public void registration() {
        modEventBus.addListener(EventPriority.LOWEST, BoundActionHandler::registerKeyBinds);

        forgeEventBus.addListener(BoundActionHandler::onClientTick);
    }
}
