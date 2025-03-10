package com.projectSer.projectServices.controllers;

import com.projectSer.projectServices.DTO.UserDTO;
import com.projectSer.projectServices.Services.UserService;
import com.projectSer.projectServices.models.user;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class usercontroller {

    private final UserService userService;

    public usercontroller(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String name, @RequestParam String password){
        user user = userService.login(name, password);
        if(user != null){
            UserDTO userDTO = new UserDTO(user.getName(), user.getRole());
            return ResponseEntity.ok(userDTO);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public user registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password
    ) {
        return userService.registerUser(name, email, password);
    }

}

