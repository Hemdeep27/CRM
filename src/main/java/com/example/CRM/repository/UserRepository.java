package com.example.CRM.repository;

import com.example.CRM.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    Boolean existsByEmailId(String emailId);

    Optional<User> findByEmailId(String emailId);

    List<User> findAllByIsAdmin(Boolean isAdmin);

    Boolean deleteByEmailId(String email);
}
