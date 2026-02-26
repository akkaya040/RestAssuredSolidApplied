package com.petstore.tests.base;

import com.petstore.framework.services.PetService;
import com.petstore.framework.services.StoreService;
import com.petstore.framework.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Test class for all API tests
 * Provides common setup, teardown, and service instances
 */
@Slf4j
public abstract class BaseTest {

    // Service instances
    protected PetService petService;
    protected StoreService storeService;
    protected UserService userService;

    protected List<Long> createdPetIds;
    protected List<Long> createdOrderIds;
    protected List<String> createdUsernames;

    @BeforeEach
    public void setUp() {
        log.info("========== Test Setup Started ==========");

        // Initialize services
        petService = new PetService();
        storeService = new StoreService();
        userService = new UserService();

        createdPetIds = new ArrayList<>();
        createdOrderIds = new ArrayList<>();
        createdUsernames = new ArrayList<>();

        log.info("Services initialized successfully");
    }

    @AfterEach
    public void tearDown() {
        log.info("========== Test Teardown Started ==========");

        createdPetIds.forEach(petId -> {
            try {
                petService.deletePet(petId);
                log.debug("Cleaned up pet with ID: {}", petId);
            } catch (Exception e) {
                log.warn("Failed to clean up pet with ID: {}", petId, e);
            }
        });

        createdOrderIds.forEach(orderId -> {
            try {
                storeService.deleteOrder(orderId);
                log.debug("Cleaned up order with ID: {}", orderId);
            } catch (Exception e) {
                log.warn("Failed to clean up order with ID: {}", orderId, e);
            }
        });

        createdUsernames.forEach(username -> {
            try {
                userService.deleteUser(username);
                log.debug("Cleaned up user with username: {}", username);
            } catch (Exception e) {
                log.warn("Failed to clean up user with username: {}", username, e);
            }
        });

        log.info("Test teardown completed");
    }

    /**
     * to track created pet for cleanup
     */
    protected void trackPetForCleanup(Long petId) {
        createdPetIds.add(petId);
    }

    /**
     * to track created order for cleanup
     */
    protected void trackOrderForCleanup(Long orderId) {
        createdOrderIds.add(orderId);
    }

    /**
     * to track created user for cleanup
     */
    protected void trackUserForCleanup(String username) {
        createdUsernames.add(username);
    }
}
