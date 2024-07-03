package utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    // Configuration File Path
    private static final String PATH =
            "src" + File.separator +
                    "main" + File.separator +
                    "resources" + File.separator +
                    "config.properties";

    /**
     * Retrieves the values from the config.properties
     *
     * @param property Name of property to retrieve
     * @return Value of retrieved property
     * @throws RuntimeException If an IOException occurs while reading the config file
     */
    public static String getProperty(String property) {
        FileReader reader;
        Properties p = new Properties();
        try {
            reader = new FileReader(PATH);
            p.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return p.getProperty(property);
    }
}
