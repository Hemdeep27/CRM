package com.example.CRM.service.impl;

import com.example.CRM.dto.LeadCreationDTO;
import com.example.CRM.entity.Lead;
import com.example.CRM.entity.User;
import com.example.CRM.repository.LeadRepository;
import com.example.CRM.repository.UserRepository;
import com.example.CRM.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeadServiceImpl implements LeadService {

    @Autowired
    LeadRepository leadRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
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

    @Override
    public Boolean assignLead(String leadId, String supportId) {
        Optional<Lead> optionalLead=leadRepository.findById(leadId);
        Lead lead=optionalLead.get();
        lead.setAssignedToId(supportId);
        lead.setCreatedTime(System.currentTimeMillis());
        Optional<User> optionalUser=userRepository.findById(supportId);
        lead.setAssignedName(optionalUser.get().getSupportName());
        leadRepository.save(lead);
        return true;
    }

    @Override
    public Boolean editLead(String leadId, String leadInfo) {
        Optional<Lead> optionalLead=leadRepository.findById(leadId);
        Lead lead=optionalLead.get();
        lead.setLeadInfo(leadInfo);
        leadRepository.save(lead);
        return true;
    }

    @Override
    public List<Lead> unassignedList() {
        List<Lead> leadList=leadRepository.findAllByAssignedToId(null);
        Collections.reverse(leadList);
        return leadList;
    }

    @Override
    public List<Lead> assignedList() {
        Query query=new Query();
        query.addCriteria(Criteria.where("assignedToId").exists(true));
        query.with(Sort.by(Sort.Direction.DESC,"createdTime"));
        List<Lead> leadList=mongoTemplate.find(query,Lead.class);
        return leadList;
    }

    @Override
    public List<Lead> leadsOfService(String serviceId) {
        Query query=new Query();
        query.addCriteria(Criteria.where("assignedToId").is(serviceId));
        query.with(Sort.by(Sort.Direction.DESC,"createdTime"));
        List<Lead> leadList=mongoTemplate.find(query,Lead.class);
        return leadList;
    }

    @Override
    public Lead getLeadByLeadId(String leadId) {
        Optional<Lead> optionalLead=leadRepository.findById(leadId);
        return optionalLead.get();
    }
}
