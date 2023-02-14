package com.example.CRM.service.impl;

import com.example.CRM.dto.LoginDTO;
import com.example.CRM.dto.RegisterDTO;
import com.example.CRM.entity.User;
import com.example.CRM.repository.UserRepository;
import com.example.CRM.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Boolean addUser(RegisterDTO registerDTO) {
        if(!userRepository.existsByEmailId(registerDTO.getEmailId())){
            if(registerDTO.getEmailId().length()<4 || registerDTO.getPassword().length() <4 || registerDTO.getContactNumber()<4 || registerDTO.getSupportName().length()<4 )
                return false;
            User user=new User();
            BeanUtils.copyProperties(registerDTO,user);
            user.setSupportId(UUID.randomUUID().toString());
            user.setIsActive(false);
            user.setCount(0);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    @Override
    public Boolean removeUser(String email){
        if(userRepository.existsByEmailId(email)){
            userRepository.deleteByEmailId(email);
            return true;
        }
        return false;
    }


    @Override
    public String login(LoginDTO loginDTO) {
        if (userRepository.existsByEmailId(loginDTO.getEmailId())){
            Optional<User> optionalUser=userRepository.findByEmailId(loginDTO.getEmailId());
            User user=optionalUser.get();
            if(user.getPassword().equals(loginDTO.getPassword())){
                user.setIsActive(true);
                userRepository.save(user);
                String id=user.getSupportId();
                if(user.getIsAdmin()){
                    id="+"+id;
                }
                else{
                    id="-"+id;
                }
                return id;
            }
        }
        return null;
    }

    @Override
    public List<User> supportList() {
        Query query=new Query();
        query.addCriteria(Criteria.where("isAdmin").is(false));
        query.with(Sort.by(Sort.Direction.ASC,"count"));
        return mongoTemplate.find(query,User.class);
    }

    @Override
    public Boolean logout(String userEmailId){
        User user=userRepository.findByEmailId(userEmailId).get();
        user.setIsActive(false);
        userRepository.save(user);
        return true;
    }

    @Override
    public List<User> supportListByActive(Boolean isActive, Boolean isAdmin) {
        Query query=new Query();
        query.addCriteria(Criteria.where("isActive").is(isActive).andOperator(Criteria.where("isAdmin").is(isAdmin)));
        query.with(Sort.by(Sort.Direction.ASC,"count"));
        return mongoTemplate.find(query,User.class);
    }

    @Override
    public User getSupportBySupportId(String supportId) {
        Optional<User> optionalUser=userRepository.findById(supportId);
//        if(optionalUser.isPresent()){
//            return null;
//        }
        return optionalUser.get();
    }
}
