package com.petstore.framework.utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.extern.slf4j.Slf4j;

/**
 * Custom RestAssured filter for logging requests and responses
 * Uses SLF4J for professional logging
 */
@Slf4j
public class RequestResponseLoggingFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
            FilterableResponseSpecification responseSpec,
            FilterContext ctx) {

        logRequest(requestSpec);

        Response response = ctx.next(requestSpec, responseSpec);

        logResponse(response);

        return response;
    }

    private void logRequest(FilterableRequestSpecification requestSpec) {
        log.info("========== REQUEST ==========");
        log.info("{} {}", requestSpec.getMethod(), requestSpec.getURI());

        if (requestSpec.getHeaders() != null && requestSpec.getHeaders().size() > 0) {
            log.debug("Headers: {}", requestSpec.getHeaders());
        }

        if (requestSpec.getBody() != null) {
            log.info("Request Body:\n{}", prettyPrint(requestSpec.getBody().toString()));
        }
    }

    private void logResponse(Response response) {
        int statusCode = response.getStatusCode();

        if (statusCode >= 400) {
            log.error("========== RESPONSE (ERROR) ==========");
            log.error("Status Code: {}", statusCode);
            log.error("Response Body:\n{}", response.getBody().asPrettyString());
        } else {
            log.info("========== RESPONSE ==========");
            log.info("Status Code: {}", statusCode);
            log.debug("Response Body:\n{}", response.getBody().asPrettyString());
        }
    }

    private String prettyPrint(String json) {
        try {
            return json;
        } catch (Exception e) {
            return json;
        }
    }
}
