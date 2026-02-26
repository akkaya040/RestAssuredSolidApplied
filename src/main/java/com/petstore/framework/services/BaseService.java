package com.petstore.framework.services;

import com.petstore.framework.config.ConfigManager;
import com.petstore.framework.utils.RequestResponseLoggingFilter;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract Base Service following SOLID principles
 * Provides common RequestSpecification for all API services
 */
@Slf4j
public abstract class BaseService {

    protected final ConfigManager config;

    protected BaseService() {
        this.config = ConfigManager.getInstance();
        log.debug("Initialized {} with base URL: {}", this.getClass().getSimpleName(), config.getBaseUrl());
    }

    /**
     * Get configured RequestSpecification with all filters and settings
     * This method can be overridden by child classes if needed (Open/Closed
     * Principle)
     */
    protected RequestSpecification getRequestSpec() {
        return RestAssured.given()
                .baseUri(config.getBaseUrl())
                .contentType(ContentType.JSON)
                .header("api_key", config.getApiKey())
                .filter(new AllureRestAssured())
                .filter(new RequestResponseLoggingFilter())
                .config(RestAssuredConfig.config()
                        .objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.JACKSON_2)));
    }
}
