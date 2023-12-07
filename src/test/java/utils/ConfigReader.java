package utils;

import java.util.Properties;

public class ConfigReader {

    private Properties properties;

    public void readConfig(String path) {
        properties = new Properties();
        try {
            properties.load(ConfigReader.class.getClassLoader().getResourceAsStream("config/"+path));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read config file!");
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
