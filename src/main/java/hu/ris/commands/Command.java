package hu.ris.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import lombok.Getter;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public abstract class Command {

    @Getter
    protected final String name;

    @Getter
    protected final LiteralArgumentBuilder<ServerCommandSource> root;

    public Command(String name) {
        this.name = name;
        this.root = CommandManager.literal(name);
    }

    public abstract void registerTo(CommandDispatcher<ServerCommandSource> dispatcher);

}
