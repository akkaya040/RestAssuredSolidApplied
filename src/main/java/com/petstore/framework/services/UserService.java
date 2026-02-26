package com.petstore.framework.services;

import com.petstore.framework.models.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * User API Service
 * Encapsulates all User-related API operations
 */
@Slf4j
public class UserService extends BaseService {

    private static final String USER_ENDPOINT = "/user";

    @Step("Create a new user")
    public Response createUser(User user) {
        log.info("Creating user with username: {}", user.getUsername());
        return getRequestSpec()
                .body(user)
                .post(USER_ENDPOINT);
    }

    @Step("Get user by username: {username}")
    public Response getUserByUsername(String username) {
        log.info("Getting user with username: {}", username);
        return getRequestSpec()
                .pathParam("username", username)
                .get(USER_ENDPOINT + "/{username}");
    }

    @Step("Update user: {username}")
    public Response updateUser(String username, User user) {
        log.info("Updating user with username: {}", username);
        return getRequestSpec()
                .pathParam("username", username)
                .body(user)
                .put(USER_ENDPOINT + "/{username}");
    }

    @Step("Delete user: {username}")
    public Response deleteUser(String username) {
        log.info("Deleting user with username: {}", username);
        return getRequestSpec()
                .pathParam("username", username)
                .delete(USER_ENDPOINT + "/{username}");
    }

    @Step("User login: {username}")
    public Response login(String username, String password) {
        log.info("User login attempt for: {}", username);
        return getRequestSpec()
                .queryParam("username", username)
                .queryParam("password", password)
                .get(USER_ENDPOINT + "/login");
    }

    @Step("User logout")
    public Response logout() {
        log.info("User logout");
        return getRequestSpec()
                .get(USER_ENDPOINT + "/logout");
    }
}
