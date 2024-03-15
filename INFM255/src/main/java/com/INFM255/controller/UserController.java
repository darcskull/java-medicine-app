package com.INFM255.controller;

import com.INFM255.data.User;
import com.INFM255.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/reg")
    public String registrationPage() {
        return "registration";
    }

    @GetMapping("/patients")
    public String getAllPatients(Model model) {
        model.addAttribute("users", userService.getAllPatients());
        return "doctors/table";
    }

    @GetMapping("/doctors")
    public String getAllDoctors(Model model) {
        model.addAttribute("users", userService.getAllDoctors());
        return "patients/table";
    }

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

}
