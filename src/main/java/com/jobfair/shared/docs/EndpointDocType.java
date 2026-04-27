package com.jobfair.shared.docs;

public enum EndpointDocType {
    CREATE("create"),
    GET_ALL("get-all"),
    GET_BY_ID("get-by-id"),
    UPDATE("update"),
    PATCH("patch"),
    DELETE("delete"),
    CUSTOM("");

    private final String snippetSuffix;

    EndpointDocType(String snippetSuffix) {
        this.snippetSuffix = snippetSuffix;
    }

    public String snippetSuffix() {
        return snippetSuffix;
    }
}
