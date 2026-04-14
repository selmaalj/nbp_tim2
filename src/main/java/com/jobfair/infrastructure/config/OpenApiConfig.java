package com.jobfair.infrastructure.config;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.DateTimeSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI jobFairOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("JobFAIR API")
                        .description("Public contract for JobFAIR backend resources, shared response envelopes, and binary CV transfer endpoints.")
                        .version("v1")
                        .contact(new Contact().name("JobFAIR Team").email("support@jobfair.ba"))
                        .license(new License().name("Internal Use")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local development"),
                        new Server().url("/").description("Relative deployment base URL")
                ))
                .tags(List.of(
                        tag("Articles", "Article publishing, lookup, and filtering endpoints."),
                        tag("Article images", "Article image associations and filter endpoints."),
                        tag("Committees", "Committee lifecycle and year-based lookups."),
                        tag("Committee members", "Committee membership relations and filters."),
                        tag("Gallery images", "Organization gallery image relations."),
                        tag("Jobs", "Job listings, search, and activity lookups."),
                        tag("Media", "Uploaded media assets and metadata."),
                        tag("Media Outlets", "Media outlet catalog and kind-based queries."),
                        tag("Media participations", "Media participation records by year and tier."),
                        tag("Organizations", "Organization catalog, types, and search."),
                        tag("Package tiers", "Participation package tiers and code lookup."),
                        tag("Participations", "Organization participation records by year."),
                        tag("People", "People directory plus CV upload/download endpoints."),
                        tag("Stat boards", "Stat board catalog, year lookup, and search.")
                ))
                .components(new Components()
                        .addSchemas("ApiResponseEnvelope", apiResponseEnvelopeSchema())
                        .addSchemas("ValidationErrorPayload", validationErrorSchema())
                        .addSchemas("BinaryDownload", new Schema<>().type("string").format("binary"))
                        .addResponses("BadRequestResponse", commonJsonResponse(
                                "Bad request",
                                validationErrorExample("Validation failed", "Request validation or parameter conversion failed.")
                        ))
                        .addResponses("NotFoundResponse", commonJsonResponse(
                                "Not found",
                                simpleErrorExample("Resource not found")
                        ))
                        .addResponses("InternalServerErrorResponse", commonJsonResponse(
                                "Internal server error",
                                simpleErrorExample("Unexpected server error")
                        )));
    }

    @Bean
    public OperationCustomizer commonResponseCustomizer() {
        return (operation, handlerMethod) -> {
            ApiResponses responses = operation.getResponses();
            addResponseIfMissing(responses, "400", componentResponse("BadRequestResponse"));
            addResponseIfMissing(responses, "404", componentResponse("NotFoundResponse"));
            addResponseIfMissing(responses, "500", componentResponse("InternalServerErrorResponse"));
            addDefaultDescription(operation, handlerMethod);
            return operation;
        };
    }

    private void addDefaultDescription(io.swagger.v3.oas.models.Operation operation, HandlerMethod handlerMethod) {
        if (operation.getDescription() == null || operation.getDescription().isBlank()) {
            operation.setDescription("Documented from Spring handler method `" + handlerMethod.getMethod().getName() + "`.");
        }
    }

    private void addResponseIfMissing(ApiResponses responses, String code, ApiResponse apiResponse) {
        if (!responses.containsKey(code)) {
            responses.addApiResponse(code, apiResponse);
        }
    }

    private ApiResponse componentResponse(String componentName) {
        return new ApiResponse().$ref("#/components/responses/" + componentName);
    }

    private ApiResponse commonJsonResponse(String description, Map<String, Object> exampleBody) {
        return new ApiResponse()
                .description(description)
                .content(new Content().addMediaType(
                        MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType()
                                .schema(new Schema<>().$ref("#/components/schemas/ApiResponseEnvelope"))
                                .addExamples("default", new Example()
                                        .summary(description)
                                        .value(exampleBody))
                ));
    }

    private Schema<?> apiResponseEnvelopeSchema() {
        return new ObjectSchema()
                .addProperty("success", new BooleanSchema())
                .addProperty("message", new Schema<String>().type("string"))
                .addProperty("data", new Schema<>().description("Endpoint-specific payload; may be an object, array, or null."))
                .addProperty("timestamp", new DateTimeSchema())
                .description("Standard JSON response envelope used across the API.");
    }

    private Schema<?> validationErrorSchema() {
        return new ObjectSchema()
                .addProperty("message", new Schema<String>().type("string"))
                .addProperty("errors", new ObjectSchema()
                        .additionalProperties(new Schema<String>().type("string")))
                .description("Field- or parameter-level validation errors.");
    }

    private Map<String, Object> validationErrorExample(String message, String detail) {
        return Map.of(
                "success", false,
                "message", message,
                "data", Map.of(
                        "message", message,
                        "errors", Map.of("field", detail)
                ),
                "timestamp", "2026-04-14T21:30:00"
        );
    }

    private Map<String, Object> simpleErrorExample(String message) {
        Map<String, Object> example = new LinkedHashMap<>();
        example.put("success", false);
        example.put("message", message);
        example.put("data", null);
        example.put("timestamp", "2026-04-14T21:30:00");
        return example;
    }

    private Tag tag(String name, String description) {
        return new Tag().name(name).description(description);
    }
}
