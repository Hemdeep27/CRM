package com.example.CRM.repository;

import com.example.CRM.entity.Lead;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LeadRepository extends MongoRepository<Lead,String> {

    List<Lead> findAllByAssignedToId(String assignedToId);
}
