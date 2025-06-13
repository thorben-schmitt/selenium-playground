package com.demoqa.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {

    public Properties loadEnvironment() throws IOException {
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("system.properties")) {
            if (input == null) {
                throw new IOException("Unable to find system.properties");
            }
            properties.load(input);
        }

        return properties;
    }
}
