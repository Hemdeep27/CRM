package com.example.CRM.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LeadCreationDTO {
    String userId;
    String action;
    String ad;
}
