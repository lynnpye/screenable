package com.pyehouse.screenable.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pyehouse.screenable.Screenable;
import com.pyehouse.screenable.common.Config;
import com.pyehouse.screenable.common.screendef.ScreenDef;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModCommands {
    public static final String CMD_screenablecommand = "screenable";
    public static final String CMD_showscreen = "show";

    @SubscribeEvent
    public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
        final CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
        ModCommands.register(commandDispatcher);
    }

    private static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> mc_command =
                Commands.literal(CMD_screenablecommand)
                        .then(
                                Commands.literal(CMD_showscreen)
                                        .then(
                                                Commands.argument(CMD_showscreen, StringArgumentType.string())
                                                        .executes((command) -> showScreenCommand(command))
                                        )
                        );
        dispatcher.register(mc_command);
    }

    private static int showScreenCommand(CommandContext<CommandSourceStack> commandContext) throws CommandSyntaxException {
        Player player = commandContext.getSource().getPlayer();
        String screenDefId = StringArgumentType.getString(commandContext, CMD_showscreen);
        ScreenDef screenDef = Config.getScreenDef(screenDefId);
        if (screenDef == null) {
            commandContext.getSource().sendFailure(Component.literal(String.format("No screenDef exists with screenDef id [%s]", screenDefId)));
            return 1;
        }
        Screenable.requestClientScreenDisplay(screenDef, player);
        commandContext.getSource().sendSuccess(Component.literal("showing screen"), true);
        return 1;
    }

}
