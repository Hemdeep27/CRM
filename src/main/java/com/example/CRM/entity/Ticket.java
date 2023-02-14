package com.example.CRM.entity;

import com.example.CRM.dto.CommentDTO;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.File;
import java.util.List;
import java.util.Map;

@Data
@ToString
@Document(collection = Ticket.COLLECTION_NAME)
public class Ticket {
    public static final String COLLECTION_NAME="ticket";
    @Id
    String ticketId;
    String userId;
    String userEmail;
    @Field
    String ticketStatus= "open";
    String assignedToId;
    String assignedToName;
    Long ticketCreatedTime;
    String ticketInfo;
    Map<String,List<String>> comments;
    List<String> doc;
    List<String> reason;
}
