package com.INFM255.controller;

import com.INFM255.data.Disease;
import com.INFM255.data.Medicine;
import com.INFM255.data.MedicineView;
import com.INFM255.data.User;
import com.INFM255.service.DiagnosisService;
import com.INFM255.service.DiseaseService;
import com.INFM255.service.MedicineService;
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
public class MedicineController {

    private final MedicineService medicineService;
    private final DiseaseService diseaseService;
    private final DiagnosisService diagnosisService;

    @GetMapping("/medicines")
    public String findMedicines(Model model) {
        List<MedicineView> medicines = medicineService.findAllMedicines();
        model.addAttribute("medicines", medicines);
        return "doctors/medicine";
    }

    @GetMapping("/form/medicine")
    public String formCreateMedicine(Model model) {
        List<Disease> diseases = diseaseService.findAllDiseases();
        model.addAttribute("diseases", diseases);
        return "doctors/createMedicine";
    }

    @PostMapping("/create/medicine")
    public ResponseEntity<Void> createMedicine(@RequestBody Map<String, String> request) {
        Medicine medicine = new Medicine();
        medicine.setName(request.get("name"));
        medicine.setDescription(request.get("description"));
        medicine.setPrice(new BigDecimal(request.get("price")));
        String diseaseName = request.get("diseaseName");
        medicineService.createMedicine(medicine, diseaseName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/personal/medicines")
    public String getPersonalMedicines(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        List<Disease> diseases = diagnosisService.findPersonalDiseases(user.getId());
        List<MedicineView> views = medicineService.getPersonalMedicines(diseases);
        model.addAttribute("medicines", views);
        return "patients/personalMedicines";
    }

}
