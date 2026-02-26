package com.petstore.tests.pet;

import com.petstore.framework.models.Pet;
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

@Feature("Pet API")
@DisplayName("Pet CRUD Operations")
public class PetCrudTest extends BaseTest {

    @Test
    @DisplayName("Should create a new pet successfully")
    @Description("Verify that a new pet can be created with valid data")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldCreatePetSuccessfully() {
        // Arrange
        Pet newPet = TestDataFactory.createRandomPet();

        // Act
        Response response = petService.createPet(newPet);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);

        Pet createdPet = response.as(Pet.class);
        assertThat(createdPet.getName()).isEqualTo(newPet.getName());
        assertThat(createdPet.getStatus()).isEqualTo(newPet.getStatus());

        // Track for cleanup
        trackPetForCleanup(createdPet.getId());
    }

    @Test
    @DisplayName("Should retrieve pet by ID")
    @Description("Verify that an existing pet can be retrieved by its ID")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldGetPetById() {
        // Arrange - Create a pet first
        Pet newPet = TestDataFactory.createRandomPet();
        Response createResponse = petService.createPet(newPet);
        Long petId = createResponse.as(Pet.class).getId();
        trackPetForCleanup(petId);

        // Act
        Response response = petService.getPetById(petId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);

        Pet retrievedPet = response.as(Pet.class);
        assertThat(retrievedPet.getId()).isEqualTo(petId);
        assertThat(retrievedPet.getName()).isEqualTo(newPet.getName());
    }

    @Test
    @DisplayName("Should update existing pet")
    @Description("Verify that an existing pet can be updated")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldUpdatePet() {
        // Arrange - Create a pet first
        Pet newPet = TestDataFactory.createRandomPet();
        Response createResponse = petService.createPet(newPet);
        Pet createdPet = createResponse.as(Pet.class);
        trackPetForCleanup(createdPet.getId());

        // Modify the pet
        createdPet.setName("Updated Name");
        createdPet.setStatus("sold");

        // Act
        Response response = petService.updatePet(createdPet);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);

        Pet updatedPet = response.as(Pet.class);
        assertThat(updatedPet.getName()).isEqualTo("Updated Name");
        assertThat(updatedPet.getStatus()).isEqualTo("sold");
    }

    @Test
    @DisplayName("Should delete pet successfully")
    @Description("Verify that a pet can be deleted")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldDeletePet() {
        // Arrange - Create a pet first
        Pet newPet = TestDataFactory.createRandomPet();
        Response createResponse = petService.createPet(newPet);
        Long petId = createResponse.as(Pet.class).getId();

        // Act
        Response response = petService.deletePet(petId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);

        // Verify pet is deleted (should return 404)
        Response getResponse = petService.getPetById(petId);
        assertThat(getResponse.getStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("Should find pets by status")
    @Description("Verify that pets can be filtered by status")
    @Severity(SeverityLevel.NORMAL)
    public void shouldFindPetsByStatus() {
        // Arrange - Create a pet with specific status
        Pet newPet = TestDataFactory.createPetWithStatus("available");
        Response createResponse = petService.createPet(newPet);
        trackPetForCleanup(createResponse.as(Pet.class).getId());

        // Act
        Response response = petService.findPetsByStatus("available");

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getList("$")).isNotEmpty();
    }
}
