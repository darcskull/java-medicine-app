package com.INFM255.controller;

import com.INFM255.data.User;
import com.INFM255.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> loginRequest, HttpSession session) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        User user = userService.verifyLogin(email, password);
        session.setAttribute("loggedInUser", user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registration(@RequestBody Map<String, String> registrationRequest) {
        User user = new User();
        user.setEmail(registrationRequest.get("email"));
        user.setPassword(registrationRequest.get("password"));
        user.setFirstName(registrationRequest.get("firstName"));
        user.setLastName(registrationRequest.get("lastName"));
        user.setPersonalNumber(registrationRequest.get("personalNumber"));
        user.setPhoneNumber(registrationRequest.get("phoneNumber"));
        user.setIsDoctor(Boolean.valueOf(registrationRequest.get("isDoctor")));
        userService.registerUser(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/test")
    public String threadTest(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("stringValue", user.toString());
        return "test";
    }

}
