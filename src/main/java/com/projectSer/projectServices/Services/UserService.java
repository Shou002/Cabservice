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

    public boolean login(String email, String password) {
        user user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    public user registerUser(String name, String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already exists!");
        }

        user newUser = new user();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);

        return userRepository.save(newUser);
    }
}

