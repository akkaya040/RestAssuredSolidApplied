package com.petstore.framework.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration Manager using Singleton pattern
 * Loads environment-specific properties from config files
 */
@Slf4j
public class ConfigManager {

    private static ConfigManager instance;
    private final Properties properties;
    private final String environment;

    private ConfigManager() {
        this.environment = System.getProperty("env", "dev");
        this.properties = loadProperties();
        log.info("ConfigManager initialized for environment: {}", environment);
    }

    /**
     * Thread-safe Singleton instance
     */
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /**
     * Load properties from environment-specific file
     */
    private Properties loadProperties() {
        Properties props = new Properties();
        String configFile = String.format("config/%s.properties", environment);

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFile)) {
            if (input == null) {
                log.error("Unable to find config file: {}", configFile);
                throw new RuntimeException("Configuration file not found: " + configFile);
            }
            props.load(input);
            log.debug("Loaded {} properties from {}", props.size(), configFile);
        } catch (IOException e) {
            log.error("Error loading configuration file: {}", configFile, e);
            throw new RuntimeException("Failed to load configuration", e);
        }

        return props;
    }

    /**
     * Get property value by key
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            log.warn("Property '{}' not found in configuration", key);
        }
        return value;
    }

    /**
     * Get property with default value
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    // Convenience methods for common properties

    public String getBaseUrl() {
        return getProperty("base.url");
    }

    public String getApiKey() {
        return getProperty("api.key");
    }

    public int getConnectionTimeout() {
        return Integer.parseInt(getProperty("timeout.connection", "10000"));
    }

    public int getResponseTimeout() {
        return Integer.parseInt(getProperty("timeout.response", "30000"));
    }

    public String getEnvironment() {
        return environment;
    }
}
