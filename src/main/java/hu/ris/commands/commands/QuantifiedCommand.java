package hu.ris.commands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import hu.ris.commands.Command;
import hu.ris.storage.QuantifiedDataPack;
import hu.ris.storage.QuantifiedSaveConnection;
import hu.ris.storage.QuantifiedServerIdentifier;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class QuantifiedCommand extends Command {

    public QuantifiedCommand() {
        super("quantified");
    }

    @Override
    public void registerTo(CommandDispatcher<ServerCommandSource> dispatcher) {

        root.executes(this::executeWithoutArguments).then(CommandManager.literal("connect").then(CommandManager.argument("save-id", StringArgumentType.string()).executes(this::executeConnect))).then(CommandManager.literal("save").executes(this::executeSave));

        dispatcher.register(root);

    }

    private int executeWithoutArguments(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> Text.literal("Quantified command executed"), false);
        return 1;
    }

    private int executeConnect(CommandContext<ServerCommandSource> context) {
        String saveId = StringArgumentType.getString(context, "save-id");

        if (QuantifiedSaveConnection.getSaveIdByServerUuid(QuantifiedServerIdentifier.getCurrentId()) != null) {
            context.getSource().sendError(Text.literal("Already connected to a save"));
            return 0;
        }

        QuantifiedSaveConnection.setSaveId(saveId);
        context.getSource().sendFeedback(() -> Text.literal("Connected to save with ID: " + saveId), false);

        return 1;
    }

    private int executeSave(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        new QuantifiedDataPack(player, player.getServerWorld(), player.getServer());

        return 1;
    }

}
