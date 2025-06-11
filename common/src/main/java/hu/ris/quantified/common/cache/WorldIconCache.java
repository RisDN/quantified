package hu.ris.quantified.common.cache;

import lombok.Getter;
import lombok.Setter;

public class WorldIconCache {

    @Getter
    @Setter
    private static String worldIconBase64 = "";

    public static boolean isChanged(String newBase64Icon) {
        return !worldIconBase64.equals(newBase64Icon);
    }

}
