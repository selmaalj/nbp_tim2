package com.jobfair.shared.docs;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MultipartDocSample {
    boolean enabled() default false;

    String fieldName() default "";

    String fileName() default "";

    String contentType() default "";

    String content() default "";
}
