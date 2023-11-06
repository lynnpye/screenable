package com.pyehouse.screenable.common.screendef;

import net.minecraft.world.entity.player.Player;

@FunctionalInterface
public interface ScreenableComponentCallback {
    void doCallback(Player player);
}
