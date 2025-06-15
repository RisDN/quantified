package hu.ris.quantified.fabric.commands;

import java.util.ArrayList;
import java.util.List;

import hu.ris.quantified.fabric.commands.commands.QuantifiedCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Commands {

    private static final List<Command> COMMANDS = new ArrayList<>();

    private static void init() {
        add(new QuantifiedCommand());
    }

    public static void register() {
        init();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            for (Command command : COMMANDS) {

                if (environment.dedicated) {
                    command.root.requires(source -> source.hasPermissionLevel(4));
                }

                command.registerTo(dispatcher);
            }
        });
    }

    private static void add(Command command) {
        COMMANDS.removeIf(existing -> existing.getName().equals(command.getName()));
        COMMANDS.add(command);
    }
}
