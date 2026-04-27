package com.jobfair.shared.docs;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.ResolvableType;
import org.springframework.web.method.HandlerMethod;

public final class EndpointDocumentationResolver {

    private EndpointDocumentationResolver() {
    }

    public static ResolvedEndpointDocumentation resolve(HandlerMethod handlerMethod) {
        Class<?> beanType = handlerMethod.getBeanType();
        ApiResourceDocumentation resource = findResourceDocumentation(beanType);
        EndpointDocumentation endpoint = findEndpointDocumentation(beanType, handlerMethod.getMethod());

        return new ResolvedEndpointDocumentation(
                resource,
                endpoint,
                snippetId(resource, endpoint),
                displayName(resource, endpoint),
                summary(resource, endpoint),
                endpoint.expectedStatus(),
                endpoint.binaryResponse(),
                Arrays.stream(endpoint.errorProfiles())
                        .map(ErrorDocProfile::partialPath)
                        .toList(),
                requestBodyType(beanType, endpoint)
        );
    }

    public static ApiResourceDocumentation findResourceDocumentation(Class<?> beanType) {
        ApiResourceDocumentation documentation = beanType.getAnnotation(ApiResourceDocumentation.class);
        if (documentation == null) {
            throw new IllegalStateException("Controller " + beanType.getName() + " is missing @ApiResourceDocumentation");
        }
        return documentation;
    }

    public static EndpointDocumentation findEndpointDocumentation(Class<?> beanType, Method method) {
        EndpointDocumentation direct = method.getAnnotation(EndpointDocumentation.class);
        if (direct != null) {
            return direct;
        }

        for (Class<?> candidate = beanType.getSuperclass(); candidate != null; candidate = candidate.getSuperclass()) {
            EndpointDocumentation inherited = findEndpointDocumentationOnType(candidate, method);
            if (inherited != null) {
                return inherited;
            }
        }

        throw new IllegalStateException("Handler method " + beanType.getName() + "#" + method.getName()
                + " is missing @EndpointDocumentation");
    }

    private static EndpointDocumentation findEndpointDocumentationOnType(Class<?> type, Method method) {
        for (Method candidate : type.getDeclaredMethods()) {
            if (candidate.getName().equals(method.getName())
                    && candidate.getParameterCount() == method.getParameterCount()) {
                EndpointDocumentation annotation = candidate.getAnnotation(EndpointDocumentation.class);
                if (annotation != null) {
                    return annotation;
                }
            }
        }
        return null;
    }

    private static String snippetId(ApiResourceDocumentation resource, EndpointDocumentation endpoint) {
        if (!endpoint.snippetId().isBlank()) {
            return endpoint.snippetId();
        }
        if (endpoint.type() == EndpointDocType.CUSTOM) {
            throw new IllegalStateException("Custom endpoint documentation requires snippetId");
        }
        return resource.snippetPrefix() + "-" + endpoint.type().snippetSuffix();
    }

    private static String displayName(ApiResourceDocumentation resource, EndpointDocumentation endpoint) {
        if (!endpoint.displayName().isBlank()) {
            return endpoint.displayName();
        }
        return switch (endpoint.type()) {
            case CREATE -> "POST /" + resource.snippetPrefix();
            case GET_ALL -> "GET /" + resource.snippetPrefix();
            case GET_BY_ID -> "GET /" + resource.snippetPrefix() + "/{id}";
            case UPDATE -> "PUT /" + resource.snippetPrefix() + "/{id}";
            case PATCH -> "PATCH /" + resource.snippetPrefix() + "/{id}";
            case DELETE -> "DELETE /" + resource.snippetPrefix() + "/{id}";
            case CUSTOM -> endpoint.snippetId();
        };
    }

    private static String summary(ApiResourceDocumentation resource, EndpointDocumentation endpoint) {
        if (!endpoint.summary().isBlank()) {
            return endpoint.summary();
        }
        return switch (endpoint.type()) {
            case CREATE -> "Create " + resource.singularName();
            case GET_ALL -> "Get all " + resource.pluralName();
            case GET_BY_ID -> "Get " + resource.singularName() + " by ID";
            case UPDATE -> "Update " + resource.singularName();
            case PATCH -> "Patch " + resource.singularName();
            case DELETE -> "Delete " + resource.singularName();
            case CUSTOM -> endpoint.displayName().isBlank() ? endpoint.snippetId() : endpoint.displayName();
        };
    }

    private static Class<?> requestBodyType(Class<?> beanType, EndpointDocumentation endpoint) {
        if (!List.of(EndpointDocType.CREATE, EndpointDocType.UPDATE, EndpointDocType.PATCH).contains(endpoint.type())) {
            return null;
        }

        ResolvableType type = ResolvableType.forClass(beanType);
        while (type != ResolvableType.NONE) {
            if ("AbstractCrudController".equals(type.toClass().getSimpleName())) {
                Class<?> requestType = type.getGeneric(1).resolve();
                if (requestType != null) {
                    return requestType;
                }
            }
            type = type.getSuperType();
        }
        throw new IllegalStateException("Could not resolve request DTO type for " + beanType.getName());
    }
}
