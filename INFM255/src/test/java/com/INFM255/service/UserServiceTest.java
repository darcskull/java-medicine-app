package com.INFM255.service;

import com.INFM255.data.User;
import com.INFM255.exception.BadRequestException;
import com.INFM255.exception.ConflictException;
import com.INFM255.exception.GeneralException;
import com.INFM255.exception.NotFoundException;
import com.INFM255.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void testGetAllPatients() {
        List<User> patients = new ArrayList<>();
        patients.add(new User());
        patients.add(new User());

        when(userRepository.findAllUsers(false)).thenReturn(patients);

        List<User> result = userService.getAllPatients();

        assertEquals(patients.size(), result.size());
        verify(userRepository, times(1)).findAllUsers(false);
    }

    @Test
    void testGetAllDoctors() {
        List<User> doctors = new ArrayList<>();
        doctors.add(new User());
        doctors.add(new User());

        when(userRepository.findAllUsers(true)).thenReturn(doctors);

        List<User> result = userService.getAllDoctors();

        assertEquals(doctors.size(), result.size());
        verify(userRepository, times(1)).findAllUsers(true);
    }

    @Test
    void testVerifyLogin_WithExistingUser() {
        String email = "test@example.com";
        String password = "password";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.findUserByEmailAndPassword(email, password)).thenReturn(user);

        User result = userService.verifyLogin(email, password);

        assertEquals(user, result);
    }

    @Test
    void testVerifyLogin_WithNonExistingUser() {
        String email = "nonexisting@example.com";
        String password = "password";

        when(userRepository.findUserByEmailAndPassword(email, password)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> userService.verifyLogin(email, password));
    }

    @Test
    void testRegisterUser_WithValidUserData() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setPhoneNumber("0888161650");
        user.setPersonalNumber("1234567890");

        userService.registerUser(user);

        verify(userRepository, times(1)).createUser(user);
    }

    @Test
    void testRegisterUser_WithEmptyValues() {
        User user = new User();
        user.setFirstName("");
        user.setLastName("");
        user.setEmail("");
        user.setPassword("");
        user.setPhoneNumber("");
        user.setPersonalNumber("");

        assertThrows(BadRequestException.class, () -> userService.registerUser(user));
    }

    @Test
    void testRegisterUser_WithExistingEmail() {
        User user = new User();
        user.setEmail("existing@example.com");

        when(userRepository.doesUserExistByEmail(anyString())).thenReturn(true);

        assertThrows(ConflictException.class, () -> userService.registerUser(user));
    }

    @Test
    void testRegisterUser_WithInvalidPhoneNumber() {
        User user = new User();
        user.setPhoneNumber("invalid");

        assertThrows(GeneralException.class, () -> userService.registerUser(user));
    }
}
