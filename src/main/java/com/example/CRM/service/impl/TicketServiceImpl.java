package com.example.CRM.service.impl;

import com.example.CRM.dto.*;
import com.example.CRM.entity.Ticket;
import com.example.CRM.entity.User;
import com.example.CRM.feign.FeignInterface;
import com.example.CRM.repository.TicketRepository;
import com.example.CRM.repository.UserRepository;
import com.example.CRM.service.TicketService;
import com.example.CRM.service.UserService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.*;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    FeignInterface feignInterface;

    @Autowired
    MongoTemplate mongoTemplate;

    public Boolean createTicket(TicketDTO ticketDTO){
        Ticket ticket=new Ticket();
        ticket.setTicketId(UUID.randomUUID().toString());
        ticket.setTicketCreatedTime(System.currentTimeMillis());
        ticket.setTicketStatus("open");
        ticket.setUserId(ticketDTO.getUserId());
        ticket.setUserEmail(ticketDTO.getUserEmail());
        ticket.setTicketInfo(ticketDTO.getTicketInfo());
        ticket.setComments(new HashMap<String,List<String>>());
        ticket.setReason(new ArrayList<String>());
        ticketRepository.insert(ticket);


        TicketStoreDTO ticketStoreDTO=new TicketStoreDTO();
        ticketStoreDTO.setTicketId(ticket.getTicketId());
        ticketStoreDTO.setTicketStatus("open");
        ticketStoreDTO.setTicketTime(ticket.getTicketCreatedTime());
        ticketStoreDTO.setUserEmail(ticketDTO.getUserEmail());
        ticketStoreDTO.setUserId(ticketDTO.getUserId());
        ticketStoreDTO.setTicketInfo(ticketDTO.getTicketInfo());
        Boolean stored=feignInterface.storeTicket(ticketStoreDTO);
        System.out.println(ticketStoreDTO);

        return Boolean.TRUE;
    }

    @Override
    public Boolean editStatus(String ticketId,String status) {
        Ticket ticket=ticketRepository.findById(ticketId).get();
        ticket.setTicketStatus(status);
        ticket.setTicketCreatedTime(System.currentTimeMillis());
        ticketRepository.save(ticket);

        Optional<User> userOptional=userRepository.findById(ticket.getAssignedToId());
        User user=userOptional.get();
        if(status.equals("closed")){
            user.setCount(user.getCount()-1);
            userRepository.save(user);
        }
        TicketStoreDTO ticketStoreDTO=new TicketStoreDTO();
        ticketStoreDTO.setTicketId(ticket.getTicketId());
        ticketStoreDTO.setTicketStatus(status);
        ticketStoreDTO.setTicketTime(ticket.getTicketCreatedTime());
        ticketStoreDTO.setSupportEmail(user.getEmailId());
        Boolean stored=feignInterface.storeTicket(ticketStoreDTO);

        return Boolean.TRUE;
    }

    @Override
    public Boolean assignTicket(String ticketId,String supportId) {
        Ticket ticket=ticketRepository.findById(ticketId).get();
        Optional<User> optionalUser=userRepository.findById(supportId);
        User user=optionalUser.get();
        user.setCount(user.getCount()+1);
        userRepository.save(user);
        ticket.setAssignedToId(supportId);
        ticket.setAssignedToName(user.getSupportName());
        ticket.setTicketStatus("assigned");
//        ticket.setReason();
        ticketRepository.save(ticket);

        TicketStoreDTO ticketStoreDTO=new TicketStoreDTO();
        ticketStoreDTO.setTicketId(ticket.getTicketId());
        ticketStoreDTO.setTicketStatus("assigned");
        ticketStoreDTO.setTicketTime(ticket.getTicketCreatedTime());
        ticketStoreDTO.setSupportEmail(user.getEmailId());
        Boolean stored=feignInterface.storeTicket(ticketStoreDTO);

        return Boolean.TRUE;
    }

    @Override
    public String assignTicketAutomatically(String ticketId){
        List<User> activeUserList=userService.supportListByActive(true,false);
        Optional<Ticket> optionalTicket=ticketRepository.findById(ticketId);
        Ticket ticket=optionalTicket.get();
        Integer activesize=activeUserList.size();
        if(activesize!=0){
            User user=activeUserList.get(0);
            ticket.setTicketStatus("assigned");
//            ticket.setReason(null);
            ticket.setAssignedToId(user.getSupportId());
            user.setCount(user.getCount()+1);
            userRepository.save(user);
            ticket.setAssignedToName(user.getSupportName());
            ticketRepository.save(ticket);
            return user.getEmailId();
        }
        List<User> userList=userService.supportListByActive(false,false);
        System.out.println(userList);
        User user=userList.get(0);
        user.setCount(user.getCount()+1);
        userRepository.save(user);
        ticket.setTicketStatus("assigned");
        ticket.setAssignedToId(user.getSupportId());
        ticket.setAssignedToName(user.getSupportName());
        ticketRepository.save(ticket);
        return user.getEmailId();
    }

    @Override
    public Ticket getTicketByTicketId(String ticketId) {
        Optional<Ticket> optionalTicket=ticketRepository.findById(ticketId);
        return optionalTicket.get();
    }

    @Override
    public Boolean rejectTicketByTicketId(String ticketId,String reason) {
        Ticket ticket= ticketRepository.findById(ticketId).get();

        User user =userRepository.findById(ticket.getAssignedToId()).get();
        user.setCount(user.getCount()-1);
        userRepository.save(user);

        ticket.setTicketStatus("open");
        List<String> reasons=ticket.getReason();
        reasons.add(ticket.getAssignedToName()+" - "+reason);
        ticket.setReason(reasons);
        ticket.setTicketCreatedTime(System.currentTimeMillis());
        ticket.setAssignedToId(null);
        ticket.setAssignedToName(null);
        ticketRepository.save(ticket);

        feignInterface.rejectTicket(ticketId,ticket.getTicketCreatedTime());


        return Boolean.TRUE;
    }


    //    @Override
//    public Boolean editTicket(EditTicketDTO editTicketDto) {
//        Ticket ticket=ticketRepository.findById(editTicketDto.getTicketId()).get();
//
//        ticket.setComments(editTicketDto.getComments());
//        ticket.setDoc(editTicketDto.getDoc());
//        ticketRepository.save(ticket);
//        return Boolean.TRUE;
//    }
    @Override
    public Boolean addComment(String supportId,String comment,String ticketId){

        Ticket ticket=ticketRepository.findById(ticketId).get();
        String supportName=userRepository.findById(supportId).get().getSupportName();
        Map<String,List<String>> commentMap=ticket.getComments();
        if(commentMap.containsKey(supportName)){
            List<String> commentsList=commentMap.get(supportName);
            commentsList.add(comment);
            commentMap.put(supportName,commentsList);
        }
        else{
            List<String> commentsList=new ArrayList<>();
            commentsList.add(comment);
            commentMap.put(supportName,commentsList);
        }
        ticket.setComments(commentMap);
        System.out.println(commentMap);
        ticketRepository.save(ticket);
        return Boolean.TRUE;

    }
    @Override
    public Boolean addDoc(AddDocDTO addDocDTO){
        Query query=new Query();
        query.addCriteria(new Criteria().where("ticketId").is(addDocDTO.getTicketId()));
        mongoTemplate.updateFirst(query,new Update().push("doc",addDocDTO.getDoc()),Ticket.class);
//        Criteria
        return Boolean.TRUE;
    }

    @Override
    public List<Ticket> assignedTicketsByStatus(String ticketStatus, String supportId) {
        Query query=new Query();
        query.addCriteria(Criteria.where("ticketStatus").is(ticketStatus).andOperator(Criteria.where("assignedToId").is(supportId)));
        query.with(Sort.by(Sort.Direction.DESC,"ticketCreatedTime"));
        List<Ticket> ticketList=mongoTemplate.find(query,Ticket.class);
        return ticketList;
    }

    @Override
    public List<Ticket> findAllByAssignedTo(String supportId) {
        Query query=new Query();
        query.addCriteria(Criteria.where("assignedToId").is(supportId));
        query.with(Sort.by(Sort.Direction.DESC,"ticketCreatedTime"));
        List<Ticket> ticketList=mongoTemplate.find(query,Ticket.class);
        return ticketList;
    }

    @Override
    public List<Ticket> findAllByTicketStatus(String status) {
        Query query=new Query();
        query.addCriteria(Criteria.where("ticketStatus").is(status));
        query.with(Sort.by(Sort.Direction.DESC,"ticketCreatedTime"));
        List<Ticket> leadList=mongoTemplate.find(query,Ticket.class);
        return leadList;
    }
}
