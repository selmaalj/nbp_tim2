package com.jobfair.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class ApiDocumentationTest extends AbstractApiDocumentationTestSupport {

    static Stream<SuccessDocSpec> documentedEndpoints() {
        return ApiDocumentationCatalog.successSpecs().stream();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("documentedEndpoints")
    void documentSuccessEndpoints(SuccessDocSpec spec) throws Exception {
        spec.stubber().accept(this);

        ResultActions result = mockMvc.perform(buildRequest(spec));
        result.andExpect(status().is(spec.expectedStatus().value()));

        if (spec.binaryResponse()) {
            result.andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                    .andExpect(header().string("Content-Disposition", org.hamcrest.Matchers.containsString("attachment;")))
                    .andDo(document(spec.snippetId()));
            return;
        }

        result.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andDo(document(
                        spec.snippetId(),
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    private MockHttpServletRequestBuilder buildRequest(SuccessDocSpec spec) throws Exception {
        if (spec.multipartFile() != null) {
            MockMultipartHttpServletRequestBuilder builder = multipart(spec.url()).file(spec.multipartFile());
            builder.with(request -> {
                request.setMethod(spec.method().name());
                return request;
            });
            return builder.accept(MediaType.APPLICATION_JSON);
        }

        MockHttpServletRequestBuilder builder;
        if (spec.method() == org.springframework.http.HttpMethod.GET) {
            builder = get(spec.url());
        } else if (spec.method() == org.springframework.http.HttpMethod.POST) {
            builder = post(spec.url());
        } else if (spec.method() == org.springframework.http.HttpMethod.PUT) {
            builder = put(spec.url());
        } else if (spec.method() == org.springframework.http.HttpMethod.PATCH) {
            builder = patch(spec.url());
        } else if (spec.method() == org.springframework.http.HttpMethod.DELETE) {
            builder = delete(spec.url());
        } else {
            throw new IllegalStateException("Unsupported method: " + spec.method());
        }

        if (spec.binaryResponse()) {
            builder.accept(MediaType.APPLICATION_PDF, MediaType.APPLICATION_OCTET_STREAM);
        } else {
            builder.accept(MediaType.APPLICATION_JSON);
        }

        if (spec.requestBody() != null) {
            builder.contentType(spec.requestContentType() == null ? MediaType.APPLICATION_JSON : spec.requestContentType());
            builder.content(objectMapper.writeValueAsBytes(spec.requestBody()));
        }

        return builder;
    }
}
