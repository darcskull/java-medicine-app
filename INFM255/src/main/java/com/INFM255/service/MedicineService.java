package com.INFM255.service;

import com.INFM255.data.Medicine;
import com.INFM255.data.Order;
import com.INFM255.exception.GeneralException;
import com.INFM255.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public List<Medicine> findAllMedicines(){
        return medicineRepository.findAllMedicines();
    }

    public void createMedicine(Medicine medicineData) {
        try {
            medicineRepository.createMedicine(medicineData);
        } catch (Exception exception) {
            throw new GeneralException("An error has occurred, the provided data was not registered");
        }
    }
}
