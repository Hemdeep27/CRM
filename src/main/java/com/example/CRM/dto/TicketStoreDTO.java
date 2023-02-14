package com.example.CRM.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TicketStoreDTO {
    String ticketId;
    String ticketStatus;
    String supportEmail;
    Long ticketTime;
    String userId;
    String userEmail;
    String ticketInfo;
}
