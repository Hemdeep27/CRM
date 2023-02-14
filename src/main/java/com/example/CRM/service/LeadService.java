package com.example.CRM.service;

import com.example.CRM.dto.LeadCreationDTO;
import com.example.CRM.entity.Lead;

import java.util.List;

public interface LeadService {
    String leadCreated(LeadCreationDTO leadCreationDTO);

    Boolean assignLead(String leadId,String supportId);

    Boolean editLead(String leadId, String leadInfo);

    List<Lead> unassignedList();

    List<Lead> assignedList();

    List<Lead> leadsOfService(String serviceId);

    Lead getLeadByLeadId(String leadId);
}
