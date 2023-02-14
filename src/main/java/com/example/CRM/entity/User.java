package com.example.CRM.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = User.COLLECTION_NAME)
@ToString
public class User {
    public static final String COLLECTION_NAME="user";
    @Id
    String supportId ;
    String emailId ;
    String password;
    String supportName;
    Long contactNumber;
    Integer count;
    Boolean isAdmin;
    Boolean isActive;
}
