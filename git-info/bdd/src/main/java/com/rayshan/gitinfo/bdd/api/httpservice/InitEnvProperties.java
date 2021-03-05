package com.rayshan.gitinfo.bdd.api.httpservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public class InitEnvProperties {
    private static final Logger log = LoggerFactory.getLogger(InitEnvProperties.class);
    private HashMap<String, String> envProperties;

    public InitEnvProperties() {
        envProperties = new HashMap<>();
        String envName = getEnvNameProperty();
        loadConfigFile(envName);
        envProperties.put("env", envName);
    }

    private static String getEnvNameProperty() {
        String defaultEnvName = InitEnvProperties.initEnvNameFromBDDPropFile();
        return InitEnvProperties.getSystemPropertyOrSetDefault("env.type", defaultEnvName);
    }

    private static String initEnvNameFromBDDPropFile() {
        String environmentConfigPath = "src/test/resources/config/application-bdd.properties";
        Properties prop = new Properties();
        InputStream iStream;
        try {
            iStream = new FileInputStream(environmentConfigPath);
            prop.load(iStream);
            log.debug("application-bdd Property File Loaded Successfully");
            return prop.getProperty("env");
        } catch (Exception e) {
            log.error("Error in loading the application-bdd environment properties file");
            return "qa";
        }
    }

    private static String getSystemPropertyOrSetDefault(String key, final String defaultValue) {
        String property = System.getProperty(key);
        if (property == null || property.isEmpty()) {
            log.info("Env value is not given and setting it to the default: " + defaultValue);
            return defaultValue;
        }
        return property;
    }

    private void loadConfigFile(String envName) {
        String environmentConfigPath = "src/test/resources/config/ENV.env.properties";
        Properties prop = new Properties();
        InputStream iStream;
        try {
            String envFilePath = environmentConfigPath.replace("ENV", envName);
            iStream = new FileInputStream(envFilePath);
            prop.load(iStream);
            log.debug("Property File Loaded Successfully");
            Set<String> propertyNames = prop.stringPropertyNames();
            for (String property : propertyNames) {
                log.debug(property + ":" + prop.getProperty(property));
                envProperties.put(property, prop.getProperty(property));
            }
        } catch (Exception e) {
            log.error("Error in loading the environment properties file");
        }
    }

    // Method to getEnvPropertyOrSetDefault boolean value or return default.
    public boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(
                getEnvPropertyOrSetDefault(key, Boolean.toString(defaultValue)));
    }

    // Method to getEnvPropertyOrSetDefault value or return default value.
    public String getEnvPropertyOrSetDefault(String key, String defaultValue) {
        Optional<String> value = Optional.ofNullable(envProperties.get(key));
        return value.orElse(defaultValue);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    // Method to getEnvPropertyOrSetDefault value from the key
    public String getEnvProperty(String key) {
        return envProperties.get(key);
    }

    public void put(String key, String value) {
        envProperties.put(key, value);
    }
}
