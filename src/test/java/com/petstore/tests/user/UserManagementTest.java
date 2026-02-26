package com.petstore.tests.user;

import com.petstore.framework.models.User;
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

@Feature("User API")
@DisplayName("User Management")
public class UserManagementTest extends BaseTest {

    @Test
    @DisplayName("Should create a new user successfully")
    @Description("Verify that a new user can be created with valid data")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldCreateUserSuccessfully() {
        // Arrange
        User newUser = TestDataFactory.createRandomUser();

        // Act
        Response response = userService.createUser(newUser);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);

        // Track for cleanup
        trackUserForCleanup(newUser.getUsername());
    }

    @Test
    @DisplayName("Should retrieve user by username")
    @Description("Verify that an existing user can be retrieved by username")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldGetUserByUsername() {
        // Arrange - Create a user first
        User newUser = TestDataFactory.createRandomUser();
        userService.createUser(newUser);
        trackUserForCleanup(newUser.getUsername());

        // Act
        Response response = userService.getUserByUsername(newUser.getUsername());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);

        User retrievedUser = response.as(User.class);
        assertThat(retrievedUser.getUsername()).isEqualTo(newUser.getUsername());
        assertThat(retrievedUser.getEmail()).isEqualTo(newUser.getEmail());
    }

    @Test
    @DisplayName("Should update existing user")
    @Description("Verify that an existing user can be updated")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldUpdateUser() {
        // Arrange - Create a user first
        User newUser = TestDataFactory.createRandomUser();
        userService.createUser(newUser);
        trackUserForCleanup(newUser.getUsername());

        // Modify the user
        newUser.setFirstName("Updated");
        newUser.setLastName("Name");

        // Act
        Response response = userService.updateUser(newUser.getUsername(), newUser);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("Should delete user successfully")
    @Description("Verify that a user can be deleted")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldDeleteUser() {
        // Arrange - Create a user first
        User newUser = TestDataFactory.createRandomUser();
        userService.createUser(newUser);

        // Act
        Response response = userService.deleteUser(newUser.getUsername());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);

        // Verify user is deleted (should return 404)
        Response getResponse = userService.getUserByUsername(newUser.getUsername());
        assertThat(getResponse.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("Should login user successfully")
    @Description("Verify that a user can login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldLoginSuccessfully() {
        // Arrange - Create a user first
        User newUser = TestDataFactory.createRandomUser();
        userService.createUser(newUser);
        trackUserForCleanup(newUser.getUsername());

        // Act
        Response response = userService.login(newUser.getUsername(), newUser.getPassword());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody().asString()).contains("logged in");
    }

    @Test
    @DisplayName("Should logout user successfully")
    @Description("Verify that a user can logout")
    @Severity(SeverityLevel.NORMAL)
    public void shouldLogoutSuccessfully() {
        // Act
        Response response = userService.logout();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);
    }
}
