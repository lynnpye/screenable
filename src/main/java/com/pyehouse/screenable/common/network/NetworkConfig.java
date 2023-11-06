package com.pyehouse.screenable.common.network;

import com.pyehouse.screenable.client.S2CScreenDisplayHandler;
import com.pyehouse.screenable.server.C2SRunActionHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;

import java.util.Optional;

import static com.pyehouse.screenable.Screenable.CHANNEL;
import static com.pyehouse.screenable.Screenable.MODID;

public class NetworkConfig {
    // message protocol version... ..
    public static final String MESSAGE_PROTOCOL_VERSION = "1";
    // channel registration indices
    public static final int S2C_SCREEN_DISPLAY = 1;
    public static final int C2S_RUN_ACTION = 2;
    // chan... .. ..
    public static final ResourceLocation channelURL = new ResourceLocation(MODID, "networkchannel");

    @SubscribeEvent
    public static void onCommonSetupEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkConfig::registration);
    }

    private static void registration() {
        CHANNEL = NetworkRegistry.newSimpleChannel(
                channelURL,
                () -> MESSAGE_PROTOCOL_VERSION,
                MESSAGE_PROTOCOL_VERSION::equals,
                MESSAGE_PROTOCOL_VERSION::equals
        );

        CHANNEL.registerMessage(
                S2C_SCREEN_DISPLAY,
                S2CScreenDisplayMessage.class,
                S2CScreenDisplayMessage::encode,
                S2CScreenDisplayMessage::decode,
                S2CScreenDisplayHandler::onScreenDisplay,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );

        CHANNEL.registerMessage(
                C2S_RUN_ACTION,
                C2SRunActionMessage.class,
                C2SRunActionMessage::encode,
                C2SRunActionMessage::decode,
                C2SRunActionHandler::onRunAction,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
    }
}
