package com.example.CRM.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = Lead.COLLECTION_NAME)
@ToString
public class Lead {
    public static final String COLLECTION_NAME="lead";
    @Id
    String leadId;
    String userId;
    String action;
    String ad;
    String assignedToId;
    String assignedName;
    String leadInfo;
    Long createdTime;
}
