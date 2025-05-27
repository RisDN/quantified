package hu.ris.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class StatUtils {
    public static List<Identifier> getAllIdentifiers() {
        List<Identifier> identifiers = new ArrayList<>();
        for (Field field : Stats.class.getDeclaredFields()) {
            if (field.getType().equals(Identifier.class)) {
                try {
                    identifiers.add((Identifier) field.get(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return identifiers;
    }

    public static List<StatType<?>> getAllStatTypes() {
        List<StatType<?>> statTypes = new ArrayList<>();
        for (Field field : Stats.class.getDeclaredFields()) {
            if (StatType.class.isAssignableFrom(field.getType())) {
                try {
                    statTypes.add((StatType<?>) field.get(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return statTypes;
    }
}
