package com.jobfair.shared.docs;

import java.util.List;

import org.springframework.http.HttpStatus;

public record ResolvedEndpointDocumentation(
        ApiResourceDocumentation resource,
        EndpointDocumentation endpoint,
        String snippetId,
        String displayName,
        String summary,
        HttpStatus expectedStatus,
        boolean binaryResponse,
        List<String> errorPartials,
        Class<?> requestBodyType
) {
}
