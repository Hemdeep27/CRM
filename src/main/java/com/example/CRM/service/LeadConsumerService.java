package com.example.CRM.service;


import com.example.CRM.dto.LeadCreationDTO;
import com.example.CRM.entity.Lead;
import com.example.CRM.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@EnableKafka
public class LeadConsumerService {

    @Autowired
    LeadRepository leadRepository;

    @KafkaListener(topics = "lead",groupId = "lead")
    public String leadCreated(LeadCreationDTO leadCreationDTO) {
        Lead lead=new Lead();
        lead.setAction(leadCreationDTO.getAction());
        lead.setAd(leadCreationDTO.getAd());
        lead.setUserId(leadCreationDTO.getUserId());
        lead.setLeadId(UUID.randomUUID().toString());
        lead.setLeadInfo("");
        lead.setCreatedTime(System.currentTimeMillis());
        leadRepository.save(lead);
        return lead.getLeadId();
    }

}
