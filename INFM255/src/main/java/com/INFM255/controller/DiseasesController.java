package com.INFM255.controller;

import com.INFM255.data.Disease;
import com.INFM255.data.User;
import com.INFM255.service.DiseaseService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DiseasesController {

    private final DiseaseService diseaseService;

    @GetMapping("/diseases")
    public String getDiseases(Model model) {
        List<Disease> diseases = diseaseService.findAllDiseases();
        model.addAttribute("diseases", diseases);
        return "doctors/disease";
    }

    @GetMapping("/form/disease")
    public String registerDiseaseView() {
        return "doctors/createDisease";
    }

    @PostMapping("/create/disease")
    public ResponseEntity<Void> createDisease(@RequestBody Map<String, String> request) {
        Disease disease = new Disease();
        disease.setType(request.get("type"));
        disease.setName(request.get("name"));
        disease.setDescription(request.get("description"));
        diseaseService.createDisease(disease);
        return ResponseEntity.ok().build();
    }

}
