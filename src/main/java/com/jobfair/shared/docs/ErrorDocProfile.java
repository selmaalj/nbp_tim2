package com.jobfair.shared.docs;

public enum ErrorDocProfile {
    JSON_BODY("partials/json-body-errors.adoc"),
    NOT_FOUND("partials/not-found-error.adoc"),
    JSON_BODY_AND_NOT_FOUND("partials/json-body-and-not-found-errors.adoc"),
    NOT_FOUND_AND_TYPE_MISMATCH("partials/not-found-and-type-mismatch-errors.adoc"),
    TYPE_MISMATCH("partials/type-mismatch-error.adoc"),
    PEOPLE_EMAIL("partials/people-email-errors.adoc"),
    CV_UPLOAD("partials/cv-upload-errors.adoc"),
    CV_DOWNLOAD("partials/cv-download-errors.adoc");

    private final String partialPath;

    ErrorDocProfile(String partialPath) {
        this.partialPath = partialPath;
    }

    public String partialPath() {
        return partialPath;
    }
}
