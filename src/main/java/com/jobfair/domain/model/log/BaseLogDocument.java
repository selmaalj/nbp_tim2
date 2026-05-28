package com.jobfair.domain.model.log;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseLogDocument {

    @Id
    private String id;

    @Field("oracle_person_id")
    private String oraclePersonId;

    @Field("title")
    private String title;

    @Field("message")
    private String message;

    @Field("status")
    private String status;

    @Field("details")
    private Map<String, Object> details = new LinkedHashMap<>();

    @CreatedDate
    @Field("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private Instant updatedAt;

    public Map<String, Object> safeDetails() {
        return details == null ? new LinkedHashMap<>() : new LinkedHashMap<>(details);
    }
}
