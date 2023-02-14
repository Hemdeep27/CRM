package com.example.CRM.controller;

import com.example.CRM.dto.AddDocDTO;
import com.example.CRM.dto.EditLeadDTO;
import com.example.CRM.dto.EditTicketDTO;
import com.example.CRM.dto.LoginDTO;
import com.example.CRM.entity.Lead;
import com.example.CRM.entity.Ticket;
import com.example.CRM.entity.User;
import com.example.CRM.service.LeadService;
import com.example.CRM.service.TicketService;
import com.example.CRM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
@RequestMapping("/support")
public class SupportController {
    @Autowired
    UserService userService;

    @Autowired
    TicketService ticketService;

    @Autowired
    LeadService leadService;

    @PostMapping("/editLead")
    public ResponseEntity<Boolean> editLead(@RequestBody EditLeadDTO editLeadDTO){
        Boolean edit=leadService.editLead(editLeadDTO.getLeadId(),editLeadDTO.getLeadInfo());
        return new ResponseEntity<Boolean>(edit,HttpStatus.OK);
    }

    @PostMapping("/editStatusOfTicket/{ticketId}/{status}")
    public ResponseEntity<Boolean> editStatus(@PathVariable("ticketId") String ticketId, @PathVariable("status") String status){
        return new ResponseEntity(ticketService.editStatus(ticketId,status),HttpStatus.CREATED);
    }

//    @PostMapping("/editTicket")
//    public ResponseEntity<Boolean> editTicket(@RequestBody EditTicketDTO editTicketDto){
//        return new ResponseEntity(ticketService.editTicket(editTicketDto),HttpStatus.OK);
//    }

    @GetMapping("/getAssignedTicketsByStatus/{ticketStatus}/{supportId}")
    public ResponseEntity<Boolean> assignedTicketsByStatus(@PathVariable("ticketStatus") String ticketStatus,@PathVariable("supportId") String supportId){
        return new ResponseEntity(ticketService.assignedTicketsByStatus(ticketStatus,supportId),HttpStatus.OK);
    }

    @GetMapping("/getTicketByTicketId/{ticketId}")
    public ResponseEntity<Ticket> getTicketByTicketId(@PathVariable("ticketId") String ticketId){
        return new ResponseEntity<>(ticketService.getTicketByTicketId(ticketId),HttpStatus.OK);
    }

    @PostMapping("/logout/{emailId}")
    public ResponseEntity<Boolean> logout(@PathVariable("emailId") String emailId){
        Boolean logout=userService.logout(emailId);
        return new ResponseEntity<>(logout,HttpStatus.OK);
    }

    @GetMapping("/getleadsBySupportId/{supportId}")
    public ResponseEntity<List<Lead>> leadsByServiceId(@PathVariable("supportId") String supportId){
        return new ResponseEntity<>(leadService.leadsOfService(supportId),HttpStatus.OK);
    }

    @GetMapping("/getLeadByLeadId/{leadId}")
    public ResponseEntity<Lead> getLeadByLeadId(@PathVariable("leadId") String leadId){
        return new ResponseEntity<>(leadService.getLeadByLeadId(leadId),HttpStatus.OK);
    }

    @GetMapping("/getSupportBySupportId/{supportId}")
    public ResponseEntity<User> getSupportBySupportId(@PathVariable("supportId") String supportId){
        return new ResponseEntity<>(userService.getSupportBySupportId(supportId),HttpStatus.OK);
    }

    @PostMapping("/rejectTicketByTicketId/{ticketId}/{reason}")
    public ResponseEntity<Boolean> rejectTicketByTicketId(@PathVariable("ticketId") String ticketId,@PathVariable("reason") String reason){
        return new ResponseEntity<>(ticketService.rejectTicketByTicketId(ticketId,reason),HttpStatus.OK);
    }

    @PostMapping("/addComment/{supportId}/{comment}/{ticketId}")
    public ResponseEntity<Boolean> addComment(@PathVariable("supportId") String supportId,@PathVariable("comment") String comment,@PathVariable("ticketId") String ticketId){
        return new ResponseEntity<>(ticketService.addComment(supportId,comment,ticketId),HttpStatus.OK);
    }

    @PostMapping("/addDoc")
    public ResponseEntity<Boolean> addDoc(@RequestBody AddDocDTO doc){
        return new ResponseEntity<>(ticketService.addDoc(doc),HttpStatus.OK);
    }

    @PostMapping("/reassignTicket/{ticketId}")
    public ResponseEntity<Boolean> reassignTicket(@PathVariable("ticketId") String ticketId){
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

}
