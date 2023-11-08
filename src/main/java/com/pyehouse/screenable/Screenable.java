package com.pyehouse.screenable;

import com.mojang.logging.LogUtils;
import com.pyehouse.screenable.client.ClientEventRegistrar;
import com.pyehouse.screenable.common.CommonEventRegistrar;
import com.pyehouse.screenable.common.Config;
import com.pyehouse.screenable.common.network.C2SRequestScreenMessage;
import com.pyehouse.screenable.common.network.C2SRunActionMessage;
import com.pyehouse.screenable.common.network.S2CScreenDisplayMessage;
import com.pyehouse.screenable.common.screendef.ScreenDef;
import com.pyehouse.screenable.server.ServerEventRegistrar;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.util.UUID;

@Mod(Screenable.MODID)
public class Screenable
{
    public static final String MODID = "screenable";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static SimpleChannel CHANNEL;
	
    public Screenable()
    {
        //Config.initConfig();

        initBusAndRegistration();
    }

    public static void logError(String msg, Object... msgargs) {
        LOGGER.error(String.format("[%s] %s", MODID, String.format(msg, msgargs)));
    }

    public static void logWarn(String msg, Object... msgargs) {
        LOGGER.warn(String.format("[%s] %s", MODID, String.format(msg, msgargs)));
    }

    public static void logInfo(String msg, Object... msgargs) {
        LOGGER.warn(String.format("[%s] %s", MODID, String.format(msg, msgargs)));
    }

    public static void requestClientScreenDisplay(ScreenDef screenDef, @Nonnull Player player) {
        requestClientScreenDisplay(screenDef, player, null);
    }

    public static void requestClientScreenDisplay(ScreenDef screenDef, @Nonnull Player player, Runnable onInsufficientPermissions) {
        if (screenDef.allowClientRequest || ServerLifecycleHooks.getCurrentServer().isSingleplayer() || player.hasPermissions(4)) {
            S2CScreenDisplayMessage message = new S2CScreenDisplayMessage(screenDef);
            CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), message);
        } else if (onInsufficientPermissions != null) {
            onInsufficientPermissions.run();
        }
    }

    public static void requestServerRunAction(String screenId, String componentId, UUID playerUUID) {
        C2SRunActionMessage message = new C2SRunActionMessage(screenId, componentId, playerUUID);
        CHANNEL.send(PacketDistributor.SERVER.noArg(), message);
    }

    public static void requestScreenForClient(String screenId) {
        C2SRequestScreenMessage message = new C2SRequestScreenMessage(screenId);
        CHANNEL.send(PacketDistributor.SERVER.noArg(), message);
    }

    private void initBusAndRegistration()
    {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        final CommonEventRegistrar commonEventRegistrar = new CommonEventRegistrar(modEventBus, forgeEventBus);
        commonEventRegistrar.registration();

        final ServerEventRegistrar serverEventRegistrar = new ServerEventRegistrar(modEventBus, forgeEventBus);
        DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> serverEventRegistrar::registration);

        final ClientEventRegistrar clientEventRegistrar = new ClientEventRegistrar(modEventBus, forgeEventBus);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> clientEventRegistrar::registration);
    }
}
