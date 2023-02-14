package com.example.CRM.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginDTO {
    String emailId;
    String password;
}
