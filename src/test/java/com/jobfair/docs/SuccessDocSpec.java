package com.jobfair.docs;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

record SuccessDocSpec(
        int resourceOrder,
        int endpointOrder,
        String sectionTitle,
        String displayName,
        String snippetId,
        String mappingKey,
        HttpMethod method,
        String url,
        Object requestBody,
        MediaType requestContentType,
        MockMultipartFile multipartFile,
        HttpStatus expectedStatus,
        boolean binaryResponse,
        List<String> errorPartials
) {

    @Override
    public String toString() {
        return snippetId;
    }
}
