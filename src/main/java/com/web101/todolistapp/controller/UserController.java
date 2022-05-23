package com.web101.todolistapp.controller;

import com.web101.todolistapp.dto.ResponseDTO;
import com.web101.todolistapp.dto.UserDTO;
import com.web101.todolistapp.model.UserEntity;
import com.web101.todolistapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    ResponseEntity<?> registerUer(@RequestBody UserDTO userDTO){
        try{
            UserEntity user = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUserName())
                    .password(userDTO.getPassword())
                    .build();

            UserEntity registeredUser = userService.create(user);

            UserDTO responseUserDto = UserDTO.builder()
                    .userName(registeredUser.getUsername())
                    .id(registeredUser.getId())
                    .email(registeredUser.getEmail())
                    .build();
            return ResponseEntity.ok().body(responseUserDto);
        }
        catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
        UserEntity user = userService.getByCredentials(
                userDTO.getEmail(),
                userDTO.getPassword()
        );

        if(user != null) {
            final UserDTO responseUserDto = UserDTO.builder()
                    .email(user.getEmail())
                    .userName(user.getUsername())
                    .id(user.getId())
                    .build();
            return ResponseEntity.ok().body(responseUserDto);
        }else{
            ResponseDTO responseDto = ResponseDTO.builder().error("Login fialed").build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }


}
