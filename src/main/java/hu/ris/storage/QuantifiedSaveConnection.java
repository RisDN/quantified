package hu.ris.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import hu.ris.config.QuantifiedConfig;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuantifiedSaveConnection {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Type MAP_TYPE = new TypeToken<Map<String, String>>() {
    }.getType();

    public static void init() {
        File file = new File(QuantifiedConfig.SAVE_FILE_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("{}");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setSaveId(String saveId) {
        UUID serverId = QuantifiedServerIdentifier.getCurrentId();
        Map<String, String> saveIdMap = loadSaveIdMap();

        saveIdMap.put(serverId.toString(), saveId);

        try (FileWriter writer = new FileWriter(QuantifiedConfig.SAVE_FILE_PATH)) {
            gson.toJson(saveIdMap, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSaveIdByServerUuid(UUID serverId) {
        Map<String, String> saveIdMap = loadSaveIdMap();
        return saveIdMap.get(serverId.toString());
    }

    private static Map<String, String> loadSaveIdMap() {
        File file = new File(QuantifiedConfig.SAVE_FILE_PATH);

        if (!file.exists()) {
            return new HashMap<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Map<String, String> map = gson.fromJson(reader, MAP_TYPE);
            return map != null ? map : new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
