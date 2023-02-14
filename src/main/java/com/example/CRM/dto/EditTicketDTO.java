package com.example.CRM.dto;

import lombok.Data;

import java.util.List;

@Data
public class EditTicketDTO {
    String ticketId;
    List<String> comments;
    List<String> doc;
}
