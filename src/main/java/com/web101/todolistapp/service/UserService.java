package com.web101.todolistapp.service;

import com.web101.todolistapp.model.UserEntity;
import com.web101.todolistapp.persistence.UserRepostory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepostory userRepostory;

    public UserEntity create(final UserEntity userEntity){
        if(userEntity == null || userEntity.getEmail() == null){
            throw new RuntimeException("Invalid arguments");
        }
        final String email = userEntity.getEmail();
        if(userRepostory.existsByEmail(email)){
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }
        return userRepostory.save(userEntity);
    }

    public UserEntity getByCredentials(final String email, final String password){
        return userRepostory.findByEmailAndPassword(email,password);
    }
}
