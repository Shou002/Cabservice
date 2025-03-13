package com.projectSer.projectServices.Services;

import com.projectSer.projectServices.models.User;
import com.projectSer.projectServices.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_withValidCredentials_shouldReturnUser() {
        // Arrange
        User mockUser = new User();
        mockUser.setName("testUser");
        mockUser.setPassword("password123");

        when(userRepository.findByName("testUser")).thenReturn(mockUser);

        // Act
        User result = userService.login("testUser", "password123");

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getName());
        verify(userRepository, times(1)).findByName("testUser");
    }

    @Test
    void login_withInvalidPassword_shouldReturnNull() {
        // Arrange
        User mockUser = new User();
        mockUser.setName("testUser");
        mockUser.setPassword("password123");

        when(userRepository.findByName("testUser")).thenReturn(mockUser);

        // Act
        User result = userService.login("testUser", "wrongPassword");

        // Assert
        assertNull(result);
        verify(userRepository, times(1)).findByName("testUser");
    }

    @Test
    void login_withNonExistentUser_shouldReturnNull() {
        // Arrange
        when(userRepository.findByName("nonExistentUser")).thenReturn(null);

        // Act
        User result = userService.login("nonExistentUser", "anyPassword");

        // Assert
        assertNull(result);
        verify(userRepository, times(1)).findByName("nonExistentUser");
    }

    @Test
    void registerUser_withUniqueCredentials_shouldSaveAndReturnUser() {
        // Arrange
        User newUser = new User();
        newUser.setName("newUser");
        newUser.setEmail("new@example.com");
        newUser.setPassword("password123");
        newUser.setRole("user");

        when(userRepository.findByEmail("new@example.com")).thenReturn(null);
        when(userRepository.findByName("newUser")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // Act
        User result = userService.registerUser("newUser", "new@example.com", "password123");

        // Assert
        assertNotNull(result);
        assertEquals("newUser", result.getName());
        assertEquals("new@example.com", result.getEmail());
        assertEquals("user", result.getRole());
        verify(userRepository, times(1)).findByEmail("new@example.com");
        verify(userRepository, times(1)).findByName("newUser");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_withExistingEmail_shouldThrowException() {
        // Arrange
        User existingUser = new User();
        existingUser.setEmail("existing@example.com");

        when(userRepository.findByEmail("existing@example.com")).thenReturn(existingUser);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser("newUser", "existing@example.com", "password123");
        });

        assertEquals("Email already exists!", exception.getMessage());
        verify(userRepository, times(1)).findByEmail("existing@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_withExistingName_shouldThrowException() {
        // Arrange
        User existingUser = new User();
        existingUser.setName("existingUser");

        when(userRepository.findByEmail("new@example.com")).thenReturn(null);
        when(userRepository.findByName("existingUser")).thenReturn(existingUser);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser("existingUser", "new@example.com", "password123");
        });

        assertEquals("Name already exists!", exception.getMessage());
        verify(userRepository, times(1)).findByEmail("new@example.com");
        verify(userRepository, times(1)).findByName("existingUser");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void addStaff_shouldSaveAndReturnStaffUser() {
        // Arrange
        User staffUser = new User();
        staffUser.setName("staffUser");
        staffUser.setEmail("staff@example.com");
        staffUser.setPassword("password123");
        staffUser.setRole("staff");

        when(userRepository.save(any(User.class))).thenReturn(staffUser);

        // Act
        User result = userService.addStaff("staffUser", "staff@example.com", "password123");

        // Assert
        assertNotNull(result);
        assertEquals("staffUser", result.getName());
        assertEquals("staff@example.com", result.getEmail());
        assertEquals("staff", result.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        // Arrange
        List<User> mockUsers = Arrays.asList(
                createUser("user1", "user1@example.com", "password1", "user"),
                createUser("user2", "user2@example.com", "password2", "user"),
                createUser("staff1", "staff1@example.com", "password3", "staff")
        );

        when(userRepository.findAll()).thenReturn(mockUsers);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(3, result.size());
        verify(userRepository, times(1)).findAll();
    }

    // Helper method to create User objects
    private User createUser(String name, String email, String password, String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }
}