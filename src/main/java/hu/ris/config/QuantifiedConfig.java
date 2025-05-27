package hu.ris.config;

public class QuantifiedConfig {

    /**
     * The interval at which data is saved, measured in ticks.
     * <p>
     * One tick is 1/20th of a second, so the default value (120 * 20 = 2400)
     * represents 120 seconds (2 minutes).
     */
    public static final int SAVE_INTERVAL = 5 * 20;

    /**
     * The URL to which the data is uploaded.
     * <p>
     * This is a placeholder URL and should be replaced with the actual URL of the
     * server to which the data will be uploaded.
     */
    public static final String UPLOAD_URL = "http://localhost:3000/api/upload";

    /*
     * The path to the file where the data is saved. <p> This file contains the
     * connections between the world and the save ID.
     */
    public static final String SAVE_FILE_PATH = "config/quantified/data.json";

}
