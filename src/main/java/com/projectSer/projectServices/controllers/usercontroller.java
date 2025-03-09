package com.projectSer.projectServices.controllers;

import com.projectSer.projectServices.Services.UserService;
import com.projectSer.projectServices.models.user;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class usercontroller {

    private final UserService userService;

    public usercontroller(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public boolean login(@RequestParam String name, @RequestParam String password) {
        return userService.login(name, password);
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

