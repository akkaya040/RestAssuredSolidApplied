package com.petstore.tests.base;

import com.github.javafaker.Faker;
import com.petstore.framework.models.Category;
import com.petstore.framework.models.Order;
import com.petstore.framework.models.Pet;
import com.petstore.framework.models.Tag;
import com.petstore.framework.models.User;

import java.util.Collections;

/**
 * Test Data Factory using Builder pattern and JavaFaker
 * Generates realistic test data for API tests
 */
public class TestDataFactory {

    private static final Faker faker = new Faker();

    /**
     * Create a random Pet with all fields populated
     */
    public static Pet createRandomPet() {
        return Pet.builder()
                .id(System.currentTimeMillis())
                .name(faker.animal().name())
                .status("available")
                .category(createRandomCategory())
                .photoUrls(Collections.singletonList(faker.internet().image()))
                .tags(Collections.singletonList(createRandomTag()))
                .build();
    }

    /**
     * Create a Pet with specific status
     */
    public static Pet createPetWithStatus(String status) {
        Pet pet = createRandomPet();
        pet.setStatus(status);
        return pet;
    }

    /**
     * Create a minimal Pet (only required fields)
     */
    public static Pet createMinimalPet() {
        return Pet.builder()
                .id(System.currentTimeMillis())
                .name(faker.animal().name())
                .photoUrls(Collections.singletonList("http://example.com/photo.jpg"))
                .build();
    }

    /**
     * Create a random Category
     */
    public static Category createRandomCategory() {
        return Category.builder()
                .id((long) faker.number().numberBetween(1, 100))
                .name(faker.commerce().department())
                .build();
    }

    /**
     * Create a random Tag
     */
    public static Tag createRandomTag() {
        return Tag.builder()
                .id((long) faker.number().numberBetween(1, 100))
                .name(faker.lorem().word())
                .build();
    }

    /**
     * Create a random Order
     */
    public static Order createRandomOrder() {
        return Order.builder()
                .id(System.currentTimeMillis())
                .petId((long) faker.number().numberBetween(1, 1000))
                .quantity(faker.number().numberBetween(1, 10))
                .shipDate("2024-12-31T00:00:00.000+0000")
                .status("placed")
                .complete(true)
                .build();
    }

    /**
     * Create an Order for a specific Pet
     */
    public static Order createOrderForPet(Long petId) {
        Order order = createRandomOrder();
        order.setPetId(petId);
        return order;
    }

    /**
     * Create a random User
     */
    public static User createRandomUser() {
        return User.builder()
                .id(System.currentTimeMillis())
                .username(faker.name().username() + System.currentTimeMillis())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .phone(faker.phoneNumber().phoneNumber())
                .userStatus(1)
                .build();
    }

    /**
     * Create a User with specific username
     */
    public static User createUserWithUsername(String username) {
        User user = createRandomUser();
        user.setUsername(username);
        return user;
    }
}
