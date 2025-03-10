package com.projectSer.projectServices.Services;

import com.projectSer.projectServices.models.user;
import com.projectSer.projectServices.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public user login(String name, String password) {
        user user = userRepository.findByName(name);
        if(user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public user registerUser(String name, String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already exists!");
        }
        if (userRepository.findByName(name) != null) {
            throw new RuntimeException("Name already exists!");
        }

        user newUser = new user();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole("user");

        return userRepository.save(newUser);
    }
}

