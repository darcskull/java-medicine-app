package com.INFM255.controller;

import com.INFM255.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/reg")
    public String registrationPage() {
        return "registration";
    }

    @GetMapping("/all")
    public String getAllPatients(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "table";
    }
}
