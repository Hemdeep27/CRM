package com.example.CRM.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TicketDTO {
    String userId;
    String userEmail;
    String ticketInfo;
}
