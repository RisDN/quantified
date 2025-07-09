package hu.ris.quantified.fabric.commands.commands;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import hu.ris.quantified.common.cache.StatisticsCache;
import hu.ris.quantified.common.config.QuantifiedConfig;
import hu.ris.quantified.fabric.Quantified;
import hu.ris.quantified.fabric.Upload;
import hu.ris.quantified.fabric.commands.Command;
import hu.ris.quantified.fabric.connect.TrySaveConnect;
import hu.ris.quantified.fabric.listeners.EndTickListener;
import hu.ris.quantified.fabric.storage.QuantifiedSaveConnection;
import hu.ris.quantified.fabric.storage.AutoSaveSettings;
import hu.ris.quantified.fabric.storage.QuantifiedServerIdentifier;
import hu.ris.quantified.fabric.storage.SaveStatus;
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

        root.executes(this::executeWithoutArguments).then(CommandManager.literal("connect").then(CommandManager.argument("save-key", StringArgumentType.string()).executes(this::executeConnect))).then(CommandManager.literal("toggle").executes(this::executeToggle)).then(CommandManager.literal("status").executes(this::executeStatus));

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

    private int executeStatus(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        // Check if save is connected
        String saveKey = QuantifiedSaveConnection.getSaveIdByServerUuid(QuantifiedServerIdentifier.getCurrentId());

        if (saveKey == null) {
            source.sendFeedback(() -> Text.translatable("quantified.status.not_connected"), false);
            return 1;
        }

        // Get status information
        String saveName = SaveStatus.getSaveName();
        long lastSuccessTime = SaveStatus.getLastSuccessTime();
        boolean autoSaveEnabled = AutoSaveSettings.isAutoSaveEnabled();

        // Send status header with mod description and website
        source.sendFeedback(() -> Text.translatable("quantified.status.header"), false);
        source.sendFeedback(() -> Text.translatable("quantified.status.website"), false);

        // Send save connection info
        if (saveName != null) {
            source.sendFeedback(() -> Text.translatable("quantified.status.connected_save", saveName), false);
        } else {
            source.sendFeedback(() -> Text.translatable("quantified.status.connected_no_name"), false);
        }

        // Send last success time
        if (lastSuccessTime > 0) {
            // Format the timestamp to a readable date/time
            Instant instant = Instant.ofEpochMilli(lastSuccessTime);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
            String formattedTime = formatter.format(instant);

            source.sendFeedback(() -> Text.translatable("quantified.status.last_sync_time", formattedTime), false);
        } else {
            source.sendFeedback(() -> Text.translatable("quantified.status.no_sync_yet"), false);
        }

        // Send auto-save status
        if (autoSaveEnabled) {
            source.sendFeedback(() -> Text.translatable("quantified.status.auto_save_on"), false);

            // Calculate time until next sync
            int currentTicks = EndTickListener.getTickCount();
            int remainingTicks = QuantifiedConfig.SAVE_INTERVAL - currentTicks;

            // Convert ticks to seconds (20 ticks = 1 second)
            int remainingSeconds = remainingTicks / 20;

            // Format as mm:ss with leading zeros
            int minutes = remainingSeconds / 60;
            int seconds = remainingSeconds % 60;
            String timeUntilNext = String.format("%02d:%02d", minutes, seconds);

            source.sendFeedback(() -> Text.translatable("quantified.status.time_until_next_sync", timeUntilNext), false);
        } else {
            source.sendFeedback(() -> Text.translatable("quantified.status.auto_save_off"), false);
        }

        return 1;
    }

}
