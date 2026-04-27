package com.jobfair.docs;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocType;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.EndpointDocumentationResolver;
import com.jobfair.shared.docs.ResolvedEndpointDocumentation;

final class ApiDocumentationSpecs {

    private ApiDocumentationSpecs() {
    }

    static List<SuccessDocSpec> successSpecs(RequestMappingHandlerMapping handlerMapping) {
        return handlerMapping.getHandlerMethods()
                .entrySet()
                .stream()
                .filter(entry -> isApplicationEndpoint(entry.getValue()))
                .flatMap(entry -> specs(entry.getKey(), entry.getValue()).stream())
                .sorted(Comparator
                        .comparingInt(SuccessDocSpec::resourceOrder)
                        .thenComparingInt(SuccessDocSpec::endpointOrder)
                        .thenComparing(SuccessDocSpec::snippetId))
                .toList();
    }

    static Set<String> documentedMappings(RequestMappingHandlerMapping handlerMapping) {
        return successSpecs(handlerMapping)
                .stream()
                .map(SuccessDocSpec::mappingKey)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    static Set<String> actualMappings(RequestMappingHandlerMapping handlerMapping) {
        return handlerMapping.getHandlerMethods()
                .entrySet()
                .stream()
                .filter(entry -> isApplicationEndpoint(entry.getValue()))
                .flatMap(entry -> mappingKeys(entry.getKey()).stream())
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private static List<SuccessDocSpec> specs(RequestMappingInfo mappingInfo, HandlerMethod handlerMethod) {
        ResolvedEndpointDocumentation documentation = EndpointDocumentationResolver.resolve(handlerMethod);
        EndpointDocumentation endpoint = documentation.endpoint();
        Object requestBody = documentation.requestBodyType() == null ? null : ApiDocumentationFixtures.requestBody(documentation.requestBodyType());
        MockMultipartFile multipartFile = ApiDocumentationFixtures.multipartFile(endpoint.multipart());

        return mappingInfo.getPatternValues()
                .stream()
                .flatMap(pattern -> requestMethods(mappingInfo).stream().map(method -> new SuccessDocSpec(
                        documentation.resource().order(),
                        endpoint.order(),
                        documentation.resource().sectionTitle(),
                        displayName(documentation, method, pattern),
                        documentation.snippetId(),
                        mappingKey(method, pattern, mappingInfo),
                        HttpMethod.valueOf(method.name()),
                        sampleUrl(pattern, endpoint, documentation),
                        requestBody,
                        multipartFile == null ? MediaType.APPLICATION_JSON : MediaType.MULTIPART_FORM_DATA,
                        multipartFile,
                        documentation.expectedStatus(),
                        documentation.binaryResponse(),
                        documentation.errorPartials()
                )))
                .toList();
    }

    private static boolean isApplicationEndpoint(HandlerMethod handlerMethod) {
        return handlerMethod.getBeanType().getPackageName().startsWith("com.jobfair.api.controller");
    }

    private static Set<RequestMethod> requestMethods(RequestMappingInfo mappingInfo) {
        Set<RequestMethod> methods = mappingInfo.getMethodsCondition().getMethods();
        if (methods.isEmpty()) {
            throw new IllegalStateException("Documentation does not support mappings without explicit HTTP methods: " + mappingInfo);
        }
        return methods;
    }

    private static Set<String> mappingKeys(RequestMappingInfo mappingInfo) {
        return mappingInfo.getPatternValues()
                .stream()
                .flatMap(pattern -> requestMethods(mappingInfo).stream().map(method -> mappingKey(method, pattern, mappingInfo)))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private static String mappingKey(RequestMethod method, String pattern, RequestMappingInfo mappingInfo) {
        String paramsSuffix = mappingInfo.getParamsCondition().isEmpty()
                ? ""
                : " [params=" + mappingInfo.getParamsCondition().getExpressions().stream()
                .map(Object::toString)
                .sorted()
                .collect(Collectors.joining(",")) + "]";
        return method.name() + " " + pattern + paramsSuffix;
    }

    private static String displayName(ResolvedEndpointDocumentation documentation, RequestMethod method, String pattern) {
        if (documentation.endpoint().type() == EndpointDocType.CUSTOM && !documentation.displayName().isBlank()) {
            return documentation.displayName();
        }
        return method.name() + " " + pattern;
    }

    private static String sampleUrl(String pattern, EndpointDocumentation endpoint, ResolvedEndpointDocumentation documentation) {
        String url = pattern;
        for (DocParameter parameter : endpoint.pathParameters()) {
            url = url.replace("{" + parameter.name() + "}", parameter.value());
        }
        if (endpoint.type() != EndpointDocType.CUSTOM) {
            url = url.replace("{id}", documentation.resource().sampleId());
        }
        if (url.contains("{")) {
            throw new IllegalStateException("Missing sample path parameter for " + documentation.snippetId() + ": " + url);
        }
        if (endpoint.queryParameters().length == 0) {
            return url;
        }
        String queryString = java.util.Arrays.stream(endpoint.queryParameters())
                .map(parameter -> parameter.name() + "=" + parameter.value())
                .collect(Collectors.joining("&"));
        return url + "?" + queryString;
    }
}
