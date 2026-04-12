package com.jobfair.api.dto.response;

public record PersonCvFileResponse(
        byte[] content,
        String fileName,
        String mimeType
) {
}
