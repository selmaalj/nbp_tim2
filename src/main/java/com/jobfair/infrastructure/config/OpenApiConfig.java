package com.jobfair.infrastructure.config;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;

import com.jobfair.shared.docs.EndpointDocumentationResolver;
import com.jobfair.shared.docs.ResolvedEndpointDocumentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.headers.Header;
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
                .components(new Components()
                        .addSchemas("ApiResponseEnvelope", apiResponseEnvelopeSchema())
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
            if (isApplicationEndpoint(handlerMethod)) {
                ResolvedEndpointDocumentation documentation = EndpointDocumentationResolver.resolve(handlerMethod);
                operation.setSummary(documentation.summary());
                operation.setDescription(documentation.resource().description());
                if (documentation.binaryResponse()) {
                    addBinaryResponse(operation.getResponses());
                }
            }

            ApiResponses responses = operation.getResponses();
            addResponseIfMissing(responses, "400", componentResponse("BadRequestResponse"));
            addResponseIfMissing(responses, "404", componentResponse("NotFoundResponse"));
            addResponseIfMissing(responses, "500", componentResponse("InternalServerErrorResponse"));
            return operation;
        };
    }

    private boolean isApplicationEndpoint(HandlerMethod handlerMethod) {
        return handlerMethod.getBeanType().getPackageName().startsWith("com.jobfair.api.controller");
    }

    private void addBinaryResponse(ApiResponses responses) {
        responses.addApiResponse("200", new ApiResponse()
                .description("Returns a downloadable binary file")
                .content(new Content()
                        .addMediaType(MediaType.APPLICATION_PDF_VALUE, binaryMediaType())
                        .addMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE, binaryMediaType()))
                .addHeaderObject(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                        new Header().description("Attachment filename").schema(new Schema<String>().type("string"))));
    }

    private io.swagger.v3.oas.models.media.MediaType binaryMediaType() {
        return new io.swagger.v3.oas.models.media.MediaType()
                .schema(new Schema<String>().type("string").format("binary"));
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

}
