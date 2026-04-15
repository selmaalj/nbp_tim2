package com.jobfair.docs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

class ApiDocumentationCoverageTest extends AbstractApiDocumentationTestSupport {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Test
    void everyApplicationEndpointHasADocumentationSpec() {
        Set<String> actualMappings = handlerMapping.getHandlerMethods()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().getBeanType().getPackageName().startsWith("com.jobfair.api.controller"))
                .flatMap(entry -> mappingKeys(entry.getKey(), entry.getValue()).stream())
                .collect(Collectors.toCollection(TreeSet::new));

        assertEquals(ApiDocumentationCatalog.documentedMappings(), actualMappings);
    }

    private Set<String> mappingKeys(RequestMappingInfo mappingInfo, HandlerMethod handlerMethod) {
        Set<RequestMethod> methods = mappingInfo.getMethodsCondition().getMethods();
        String paramsSuffix = mappingInfo.getParamsCondition().isEmpty()
                ? ""
                : " [params=" + mappingInfo.getParamsCondition().getExpressions().stream()
                .map(Object::toString)
                .sorted()
                .collect(Collectors.joining(",")) + "]";

        return mappingInfo.getPatternValues()
                .stream()
                .flatMap(pattern -> methods.stream().map(method -> method.name() + " " + pattern + paramsSuffix))
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
