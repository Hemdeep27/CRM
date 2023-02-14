package com.example.CRM.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    String emailId ;
    String password;
    String supportName;
    Long contactNumber;
    Boolean isAdmin;
}
