package com.jobfair.docs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

class ApiReferenceGenerationTest extends AbstractApiDocumentationTestSupport {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Test
    void generatedApiReferenceIsStableAndComplete() throws IOException {
        List<SuccessDocSpec> specs = ApiDocumentationSpecs.successSpecs(handlerMapping);
        String generated = ApiReferenceGenerator.render(specs);

        Path outputPath = generatedApiReferencePath();
        Files.createDirectories(outputPath.getParent());
        Files.writeString(outputPath, generated, StandardCharsets.UTF_8);

        assertFalse(specs.isEmpty());
        assertTrue(generated.startsWith("== API Reference"));
        assertTrue(generated.contains("=== Articles"));
        assertTrue(generated.contains("=== Stat Boards"));
        assertEquals(generated, ApiReferenceGenerator.render(specs));

        for (SuccessDocSpec spec : specs) {
            assertTrue(generated.contains("include::{snippets}/" + spec.snippetId() + "/http-request.adoc[]")
                    || spec.binaryResponse());
            assertTrue(generated.contains("include::{snippets}/" + spec.snippetId() + "/http-response.adoc[]"));
        }
    }

    private Path generatedApiReferencePath() {
        String configured = System.getProperty("jobfair.generatedApiReferencePath");
        if (configured != null && !configured.isBlank()) {
            return Path.of(configured);
        }
        return Path.of("target/generated-docs-src/api-reference.adoc");
    }

}
