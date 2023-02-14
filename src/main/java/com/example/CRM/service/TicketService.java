package com.example.CRM.service;

import com.example.CRM.dto.AddDocDTO;
import com.example.CRM.dto.EditTicketDTO;
import com.example.CRM.dto.TicketDTO;
import com.example.CRM.entity.Ticket;

import java.util.List;

public interface TicketService {
    Boolean createTicket(TicketDTO ticketDTO);
    Boolean editStatus(String ticketId,String status);
    Boolean assignTicket(String ticketId,String supportId);
//    Boolean editTicket(EditTicketDTO editTicketDto);
    List<Ticket> assignedTicketsByStatus(String ticketStatus, String supportId);
    List<Ticket> findAllByAssignedTo(String supportId);
    List<Ticket> findAllByTicketStatus(String status);
    String assignTicketAutomatically(String ticketId);
    Ticket getTicketByTicketId(String ticketId);
    Boolean rejectTicketByTicketId(String ticketId,String reason);
//     Boolean addComment(String comment,String ticketId);
    Boolean addDoc(AddDocDTO doc);
    Boolean addComment(String supportId,String comment,String ticketId);
}
