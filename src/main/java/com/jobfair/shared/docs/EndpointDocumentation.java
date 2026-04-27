package com.jobfair.shared.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.http.HttpStatus;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EndpointDocumentation {
    EndpointDocType type() default EndpointDocType.CUSTOM;

    int order() default 100;

    String snippetId() default "";

    String displayName() default "";

    String summary() default "";

    HttpStatus expectedStatus() default HttpStatus.OK;

    boolean binaryResponse() default false;

    ErrorDocProfile[] errorProfiles() default {};

    DocParameter[] pathParameters() default {};

    DocParameter[] queryParameters() default {};

    MultipartDocSample multipart() default @MultipartDocSample;
}
