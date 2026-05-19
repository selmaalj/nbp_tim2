package com.jobfair.domain.model.log;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "status_histories")
public class StatusHistory extends BaseLogDocument {
}