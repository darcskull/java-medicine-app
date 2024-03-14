package com.INFM255.controller;

import com.INFM255.data.MedicineView;
import com.INFM255.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping("/medicines")
    public String findMedicines(Model model) {
        List<MedicineView> medicines = medicineService.findAllMedicines();
        model.addAttribute("medicines", medicines);
        return "doctors/medicine";
    }

}
