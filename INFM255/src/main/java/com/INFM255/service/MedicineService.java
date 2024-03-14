package com.INFM255.service;

import com.INFM255.data.Medicine;
import com.INFM255.data.MedicineView;
import com.INFM255.exception.GeneralException;
import com.INFM255.repository.DiseaseRepository;
import com.INFM255.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.INFM255.mappers.ViewMapper.mapToMedicineView;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final DiseaseRepository diseaseRepository;

    public List<MedicineView> findAllMedicines() {
        List<Medicine> meds = medicineRepository.findAllMedicines();
        List<MedicineView> views = new ArrayList<>();
        for (Medicine med : meds) {
            MedicineView view = mapToMedicineView(med, diseaseRepository.findDiseaseById(med.getDiseaseId()).getName());
            views.add(view);
        }
        return views;
    }

    public void createMedicine(Medicine medicineData) {
        try {
            medicineRepository.createMedicine(medicineData);
        } catch (Exception exception) {
            throw new GeneralException("An error has occurred, the provided data was not registered");
        }
    }
}
