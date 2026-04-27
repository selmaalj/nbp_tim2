package com.jobfair.shared.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ApiResourceDocumentation {
    int order();

    String singularName();

    String pluralName();

    String sectionTitle();

    String snippetPrefix();

    String sampleId();

    String description();
}
