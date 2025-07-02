package hu.ris.quantified.fabric.commands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import hu.ris.quantified.common.cache.StatisticsCache;
import hu.ris.quantified.fabric.Quantified;
import hu.ris.quantified.fabric.Upload;
import hu.ris.quantified.fabric.commands.Command;
import hu.ris.quantified.fabric.connect.TrySaveConnect;
import hu.ris.quantified.fabric.storage.QuantifiedSaveConnection;
import hu.ris.quantified.fabric.storage.AutoSaveSettings;
import hu.ris.quantified.fabric.storage.QuantifiedServerIdentifier;
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

        root.executes(this::executeWithoutArguments).then(CommandManager.literal("resetcache").executes(this::executeResetCache)).then(CommandManager.literal("connect").then(CommandManager.argument("save-key", StringArgumentType.string()).executes(this::executeConnect))).then(CommandManager.literal("save").executes(this::executeSave))
                .then(CommandManager.literal("toggle").executes(this::executeToggle));

        dispatcher.register(root);

    }

    private int executeWithoutArguments(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> Text.translatable("quantified.mod_description"), false);
        return 1;
    }

    private int executeResetCache(CommandContext<ServerCommandSource> context) {
        StatisticsCache.clearCache();
        return 1;
    }

    private int executeConnect(CommandContext<ServerCommandSource> context) {
        String saveKey = StringArgumentType.getString(context, "save-key");

        if (QuantifiedSaveConnection.getSaveIdByServerUuid(QuantifiedServerIdentifier.getCurrentId()) != null) {
            context.getSource().sendError(Text.translatable("quantified.connect.save-already-connected"));
            return 0;
        }

        TrySaveConnect.tryConnect(context.getSource().getPlayer(), saveKey, QuantifiedServerIdentifier.getCurrentId().toString());
        return 1;
    }

    private int executeSave(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();

        Quantified.log("Saving stats for player from command: " + player.getName().getString());
        Upload.uploadStats(player.getServer());

        return 1;
    }

    private int executeToggle(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        boolean currentSetting = AutoSaveSettings.isAutoSaveEnabled();
        boolean newSetting = !currentSetting;

        AutoSaveSettings.setAutoSaveEnabled(source.getServer(), newSetting);

        if (newSetting) {
            source.sendFeedback(() -> Text.translatable("quantified.toggle.auto_save_enabled"), false);
        } else {
            source.sendFeedback(() -> Text.translatable("quantified.toggle.auto_save_disabled"), false);
        }

        return 1;
    }

}
