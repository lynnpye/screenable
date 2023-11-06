package com.pyehouse.screenable;

import net.minecraftforge.eventbus.api.IEventBus;

public abstract class AbstractEventRegistrar {
    protected final IEventBus modEventBus;
    protected final IEventBus forgeEventBus;

    public AbstractEventRegistrar(IEventBus modEventBus, IEventBus forgeEventBus) {
        this.modEventBus = modEventBus;
        this.forgeEventBus = forgeEventBus;
    }

    public abstract void registration();
}
