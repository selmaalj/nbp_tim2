package com.jobfair.docs;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.jobfair.shared.exception.ResourceNotFoundException;

@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class ApiErrorDocumentationTest extends AbstractApiDocumentationTestSupport {

    @Test
    void documentInvalidBodyValidation() throws Exception {
        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andDo(document("errors-invalid-body", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    void documentMissingRequestParameter() throws Exception {
        mockMvc.perform(get("/people/email").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andDo(document("errors-missing-parameter", preprocessResponse(prettyPrint())));
    }

    @Test
    void documentMethodParameterValidationFailure() throws Exception {
        mockMvc.perform(get("/people/email")
                        .queryParam("value", "not-an-email")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andDo(document("errors-invalid-parameter", preprocessResponse(prettyPrint())));
    }

    @Test
    void documentTypeConversionFailure() throws Exception {
        mockMvc.perform(get("/organizations/type/INVALID")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andDo(document("errors-type-mismatch", preprocessResponse(prettyPrint())));
    }

    @Test
    void documentMalformedJsonFailure() throws Exception {
        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"title\":"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andDo(document("errors-malformed-json", preprocessResponse(prettyPrint())));
    }

    @Test
    void documentResourceNotFoundFailure() throws Exception {
        when(articleService.getById(anyString())).thenThrow(new ResourceNotFoundException("Article not found"));

        mockMvc.perform(get("/articles/article-missing").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andDo(document("errors-resource-not-found", preprocessResponse(prettyPrint())));
    }

    @Test
    void documentMissingCvFileFailure() throws Exception {
        mockMvc.perform(multipart("/people/person-1/cv")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andDo(document("people-upload-cv-missing-file", preprocessResponse(prettyPrint())));
    }

    @Test
    void documentInvalidCvFileFailure() throws Exception {
        MockMultipartFile invalidFile = new MockMultipartFile("file", "resume.txt", MediaType.TEXT_PLAIN_VALUE, "plain-text".getBytes());
        doThrow(new IllegalArgumentException("Only PDF files are supported"))
                .when(personService)
                .uploadCv(anyString(), any());

        mockMvc.perform(multipart("/people/person-1/cv")
                        .file(invalidFile)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andDo(document("people-upload-cv-invalid-file", preprocessResponse(prettyPrint())));
    }

    @Test
    void documentCvNotFoundFailure() throws Exception {
        when(personService.getCv(anyString())).thenThrow(new ResourceNotFoundException("CV not found"));

        mockMvc.perform(get("/people/person-1/cv"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andDo(document("people-get-cv-not-found", preprocessResponse(prettyPrint())));
    }
}
