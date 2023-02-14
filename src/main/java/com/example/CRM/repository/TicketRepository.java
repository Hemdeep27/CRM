package com.example.CRM.repository;

import com.example.CRM.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends MongoRepository<Ticket,String> {
}
