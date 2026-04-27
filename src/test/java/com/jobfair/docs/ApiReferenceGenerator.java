package com.jobfair.docs;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class ApiReferenceGenerator {

    private ApiReferenceGenerator() {
    }

    static String render(List<SuccessDocSpec> specs) {
        StringBuilder output = new StringBuilder("== API Reference\n\n");
        Map<String, List<SuccessDocSpec>> sections = groupBySection(specs);

        for (Map.Entry<String, List<SuccessDocSpec>> section : sections.entrySet()) {
            output.append("=== ").append(section.getKey()).append("\n\n");
            for (SuccessDocSpec spec : section.getValue()) {
                output.append("==== `").append(spec.displayName()).append("`\n");
                output.append("include::{snippets}/").append(spec.snippetId()).append("/http-request.adoc[]\n");
                output.append("include::{snippets}/").append(spec.snippetId()).append("/http-response.adoc[]\n");
                for (String errorPartial : spec.errorPartials()) {
                    output.append("include::{docsPartialsDir}/")
                            .append(errorPartial.substring("partials/".length()))
                            .append("[]\n");
                }
                output.append('\n');
            }
        }

        return output.toString();
    }

    private static Map<String, List<SuccessDocSpec>> groupBySection(List<SuccessDocSpec> specs) {
        Map<String, List<SuccessDocSpec>> sections = new LinkedHashMap<>();
        for (SuccessDocSpec spec : specs) {
            sections.computeIfAbsent(spec.sectionTitle(), ignored -> new java.util.ArrayList<>()).add(spec);
        }
        return sections;
    }
}
