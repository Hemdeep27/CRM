package com.example.CRM.service;

import com.example.CRM.dto.LoginDTO;
import com.example.CRM.dto.RegisterDTO;
import com.example.CRM.entity.User;

import java.util.List;

public interface UserService {
    Boolean addUser(RegisterDTO registerDTO);
    Boolean removeUser(String email);


    String login(LoginDTO loginDTO);

    List<User> supportList();

    Boolean logout(String userEmailId);

    List<User> supportListByActive(Boolean isActive,Boolean isAdmin);

    User getSupportBySupportId(String supportId);
}
