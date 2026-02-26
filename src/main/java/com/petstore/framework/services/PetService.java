package com.petstore.framework.services;

import com.petstore.framework.models.Pet;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * Pet API Service
 * Encapsulates all Pet-related API operations
 */
@Slf4j
public class PetService extends BaseService {

    private static final String PET_ENDPOINT = "/pet";

    @Step("Create a new pet")
    public Response createPet(Pet pet) {
        log.info("Creating pet with name: {}", pet.getName());
        return getRequestSpec()
                .body(pet)
                .post(PET_ENDPOINT);
    }

    @Step("Update existing pet")
    public Response updatePet(Pet pet) {
        log.info("Updating pet with ID: {}", pet.getId());
        return getRequestSpec()
                .body(pet)
                .put(PET_ENDPOINT);
    }

    @Step("Get pet by ID: {petId}")
    public Response getPetById(Long petId) {
        log.info("Getting pet with ID: {}", petId);
        return getRequestSpec()
                .pathParam("petId", petId)
                .get(PET_ENDPOINT + "/{petId}");
    }

    @Step("Delete pet by ID: {petId}")
    public Response deletePet(Long petId) {
        log.info("Deleting pet with ID: {}", petId);
        return getRequestSpec()
                .pathParam("petId", petId)
                .delete(PET_ENDPOINT + "/{petId}");
    }

    @Step("Find pets by status: {status}")
    public Response findPetsByStatus(String status) {
        log.info("Finding pets with status: {}", status);
        return getRequestSpec()
                .queryParam("status", status)
                .get(PET_ENDPOINT + "/findByStatus");
    }
}
