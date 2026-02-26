package com.petstore.framework.services;

import com.petstore.framework.models.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * Store API Service
 * Encapsulates all Store/Order-related API operations
 */
@Slf4j
public class StoreService extends BaseService {

    private static final String STORE_ORDER_ENDPOINT = "/store/order";

    @Step("Place a new order")
    public Response placeOrder(Order order) {
        log.info("Placing order with ID: {}", order.getId());
        return getRequestSpec()
                .body(order)
                .post(STORE_ORDER_ENDPOINT);
    }

    @Step("Get order by ID: {orderId}")
    public Response getOrderById(Long orderId) {
        log.info("Getting order with ID: {}", orderId);
        return getRequestSpec()
                .pathParam("orderId", orderId)
                .get(STORE_ORDER_ENDPOINT + "/{orderId}");
    }

    @Step("Delete order by ID: {orderId}")
    public Response deleteOrder(Long orderId) {
        log.info("Deleting order with ID: {}", orderId);
        return getRequestSpec()
                .pathParam("orderId", orderId)
                .delete(STORE_ORDER_ENDPOINT + "/{orderId}");
    }

    @Step("Get store inventory")
    public Response getInventory() {
        log.info("Getting store inventory");
        return getRequestSpec()
                .get("/store/inventory");
    }
}
