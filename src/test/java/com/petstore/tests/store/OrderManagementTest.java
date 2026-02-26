package com.petstore.tests.store;

import com.petstore.framework.models.Order;
import com.petstore.tests.base.BaseTest;
import com.petstore.tests.base.TestDataFactory;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Store API")
@DisplayName("Order Management")
public class OrderManagementTest extends BaseTest {

    @Test
    @DisplayName("Should place a new order successfully")
    @Description("Verify that a new order can be placed")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldPlaceOrderSuccessfully() {
        // Arrange
        Order newOrder = TestDataFactory.createRandomOrder();

        // Act
        Response response = storeService.placeOrder(newOrder);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);

        Order placedOrder = response.as(Order.class);
        assertThat(placedOrder.getId()).isEqualTo(newOrder.getId());
        assertThat(placedOrder.getStatus()).isEqualTo(newOrder.getStatus());

        // Track for cleanup
        trackOrderForCleanup(placedOrder.getId());
    }

    @Test
    @DisplayName("Should retrieve order by ID")
    @Description("Verify that an existing order can be retrieved by its ID")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldGetOrderById() {
        // Arrange - Place an order first
        Order newOrder = TestDataFactory.createRandomOrder();
        Response createResponse = storeService.placeOrder(newOrder);
        Long orderId = createResponse.as(Order.class).getId();
        trackOrderForCleanup(orderId);

        // Act
        Response response = storeService.getOrderById(orderId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);

        Order retrievedOrder = response.as(Order.class);
        assertThat(retrievedOrder.getId()).isEqualTo(orderId);
    }

    @Test
    @DisplayName("Should delete order successfully")
    @Description("Verify that an order can be deleted")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldDeleteOrder() {
        // Arrange - Place an order first
        Order newOrder = TestDataFactory.createRandomOrder();
        Response createResponse = storeService.placeOrder(newOrder);
        Long orderId = createResponse.as(Order.class).getId();

        // Act
        Response response = storeService.deleteOrder(orderId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);

        // Verify order is deleted (should return 404)
        Response getResponse = storeService.getOrderById(orderId);
        assertThat(getResponse.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("Should get store inventory")
    @Description("Verify that store inventory can be retrieved")
    @Severity(SeverityLevel.NORMAL)
    public void shouldGetInventory() {
        // Act
        Response response = storeService.getInventory();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getMap("$")).isNotEmpty();
    }
}
