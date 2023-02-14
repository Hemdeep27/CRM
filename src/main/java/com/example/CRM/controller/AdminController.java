package com.example.CRM.controller;

import com.example.CRM.dto.*;
import com.example.CRM.entity.Lead;
import com.example.CRM.entity.Ticket;
import com.example.CRM.entity.User;
import com.example.CRM.service.LeadService;
import com.example.CRM.service.TicketService;
import com.example.CRM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    LeadService leadService;

    @Autowired
    TicketService ticketService;

    @PostMapping("/addUser")
    public ResponseEntity<Boolean> addUser(@RequestBody RegisterDTO registerDTO){
        Boolean added=userService.addUser(registerDTO);
        return new ResponseEntity<Boolean>(added,added? HttpStatus.CREATED:HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/removeUser")
    public ResponseEntity<Boolean> removeUser(@PathVariable("userEmail") String email){
        Boolean removed=userService.removeUser(email);
        return new ResponseEntity<Boolean>(removed,removed? HttpStatus.CREATED:HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<String> Login(@RequestBody LoginDTO loginDTO){
        String login=userService.login(loginDTO);
        if(login==null){
            return new ResponseEntity<>(login,HttpStatus.BAD_REQUEST);
        }
        else if(login.charAt(0)=='+'){
            return new ResponseEntity<>(login.substring(1),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>(login.substring(1),HttpStatus.CREATED);
        }
    }

    @GetMapping("/supportList")
    public ResponseEntity<List<User>> supportList(){
        return new ResponseEntity<>(userService.supportList(),HttpStatus.OK);
    }

    @PostMapping("/createLead")
    public ResponseEntity<String> createLead(@RequestBody LeadCreationDTO leadCreationDTO){
        String leadId=leadService.leadCreated(leadCreationDTO);
        return new ResponseEntity<String>(leadId,HttpStatus.OK);
    }

    @PostMapping("/assignLead/{leadId}/{supportId}")
    public ResponseEntity<Boolean> assignLead(@PathVariable("leadId") String leadId, @PathVariable("supportId") String supportId){
        Boolean assigned=leadService.assignLead(leadId,supportId);
        return new ResponseEntity<Boolean>(assigned,HttpStatus.OK);
    }

    @GetMapping("/unassignedLeadList")
    public ResponseEntity<List<Lead>> unassignedList(){
        return new ResponseEntity<>(leadService.unassignedList(),HttpStatus.OK);
    }

    @GetMapping("/assignedLeadList")
    public ResponseEntity<List<Lead>> assignedList(){
        return new ResponseEntity<>(leadService.assignedList(),HttpStatus.OK);
    }

    @PostMapping("/createTicket")
    public ResponseEntity<Boolean> createTicket(@RequestBody TicketDTO ticketDTO){
        return new ResponseEntity(ticketService.createTicket(ticketDTO),HttpStatus.CREATED);
    }

    @PostMapping("/assignTicket/{ticketId}/{supportId}")
    public ResponseEntity<Boolean> assignSupport(@PathVariable("ticketId") String ticketId,@PathVariable("supportId") String supportId) {
        return new ResponseEntity(ticketService.assignTicket(ticketId, supportId), HttpStatus.CREATED);
    }


    @GetMapping("/getAllByTicketStatus/{ticketStatus}")
    public ResponseEntity<List<Ticket>> getAllByTicketStatus(@PathVariable("ticketStatus") String status){
        return new ResponseEntity<>(ticketService.findAllByTicketStatus(status),HttpStatus.OK);
    }

    @PostMapping("/assignTicketAutomatically")
    public String assignTicketAutomatically(@RequestParam String ticketId){
        return ticketService.assignTicketAutomatically(ticketId);
    }
}
