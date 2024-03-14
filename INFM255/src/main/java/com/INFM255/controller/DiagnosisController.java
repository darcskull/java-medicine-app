package com.INFM255.controller;

import com.INFM255.data.DiagnosisView;
import com.INFM255.data.Disease;
import com.INFM255.data.User;
import com.INFM255.service.DiagnosisService;
import com.INFM255.service.DiseaseService;
import com.INFM255.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DiagnosisController {

    private final DiagnosisService diagnosisService;
    private final UserService userService;
    private final DiseaseService diseaseService;

    @GetMapping("/diagnosis")
    public String findDiagnosis(Model model) {
        List<DiagnosisView> diagnosisViews = diagnosisService.findAllDiagnosis();
        model.addAttribute("diagnosisViews", diagnosisViews);
        return "doctors/diagnosis";
    }

    @GetMapping("/create/diagnose")
    public String registerDiagnosisView(Model model) {
        List<User> users = userService.getAllPatients();
        List<Disease> diseases = diseaseService.findAllDiseases();
        model.addAttribute("users", users);
        model.addAttribute("diseases", diseases);
        return "doctors/createDiagnosis";
    }



}
