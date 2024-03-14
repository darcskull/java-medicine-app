package com.INFM255.controller;

import com.INFM255.data.Disease;
import com.INFM255.data.Order;
import com.INFM255.data.OrderView;
import com.INFM255.data.User;
import com.INFM255.service.DiseaseService;
import com.INFM255.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DiseasesController {

    private final DiseaseService diseaseService;

    @GetMapping("/diseases")
    public String findOrdersForUser(Model model) {
        List<Disease> diseases = diseaseService.findAllDiseases();
        model.addAttribute("diseases", diseases);
        return "doctors/disease";
    }

    @PostMapping("/create/disease")
    public ResponseEntity<Void> createOrder(@RequestBody Map<String, String> request, HttpSession session) {

        return ResponseEntity.ok().build();
    }

}
