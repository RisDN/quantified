package hu.ris.commands.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class EnumArgumentType<T extends Enum<T>> implements ArgumentType<T> {

    private final Class<T> enumClass;

    public EnumArgumentType(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public T parse(StringReader reader) throws CommandSyntaxException {
        String input = reader.readUnquotedString().toUpperCase();
        return Enum.valueOf(enumClass, input);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(com.mojang.brigadier.context.CommandContext<S> context, SuggestionsBuilder builder) {
        for (T constant : enumClass.getEnumConstants()) {
            builder.suggest(constant.name().toLowerCase());
        }
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return Arrays.stream(enumClass.getEnumConstants()).map(e -> e.name().toLowerCase()).toList();
    }

    public static <T extends Enum<T>> EnumArgumentType<T> enumArgument(Class<T> enumClass) {
        return new EnumArgumentType<>(enumClass);
    }
}
