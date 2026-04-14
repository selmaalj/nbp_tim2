package com.jobfair.docs;

import java.util.function.Consumer;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

record SuccessDocSpec(
        String snippetId,
        String mappingKey,
        HttpMethod method,
        String url,
        Object requestBody,
        MediaType requestContentType,
        MockMultipartFile multipartFile,
        HttpStatus expectedStatus,
        boolean binaryResponse,
        Consumer<AbstractApiDocumentationTestSupport> stubber
) {

    @Override
    public String toString() {
        return snippetId;
    }
}
