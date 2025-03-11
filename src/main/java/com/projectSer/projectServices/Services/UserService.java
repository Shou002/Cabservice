package com.projectSer.projectServices.Services;

import com.projectSer.projectServices.models.User;
import com.projectSer.projectServices.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String name, String password) {
        User user = userRepository.findByName(name);
        if(user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User registerUser(String name, String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already exists!");
        }
        if (userRepository.findByName(name) != null) {
            throw new RuntimeException("Name already exists!");
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole("user");

        return userRepository.save(newUser);
    }

    public User addStaff(String name, String email, String password) {
        User staff = new User();
        staff.setName(name);
        staff.setEmail(email);
        staff.setPassword(password);
        staff.setRole("staff"); // Set role as "staff"

        return userRepository.save(staff);
    }

}

