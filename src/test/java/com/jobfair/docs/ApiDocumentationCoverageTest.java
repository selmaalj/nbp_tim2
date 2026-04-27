package com.jobfair.docs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

class ApiDocumentationCoverageTest extends AbstractApiDocumentationTestSupport {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Test
    void everyApplicationEndpointHasADocumentationSpec() {
        Set<String> actualMappings = ApiDocumentationSpecs.actualMappings(handlerMapping);
        Set<String> documentedMappings = ApiDocumentationSpecs.documentedMappings(handlerMapping);

        assertEquals(documentedMappings, actualMappings);
    }
}
