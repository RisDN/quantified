package hu.ris.quantified.common.upload;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class WorldIconUtils {
    public static String toBase64(Path path) {
        File iconFile = path.toFile();
        byte[] iconData = null;
        if (iconFile.exists() && iconFile.isFile()) {
            try {
                iconData = Files.readAllBytes(iconFile.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (iconData != null && iconData.length > 0) {
            return "data:image/png;base64," + java.util.Base64.getEncoder().encodeToString(iconData);
        } else {
            return null; // No icon found or error reading the file
        }
    }
}
