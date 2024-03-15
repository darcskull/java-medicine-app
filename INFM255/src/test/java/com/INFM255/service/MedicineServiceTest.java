package com.INFM255.service;

import com.INFM255.data.Disease;
import com.INFM255.data.Medicine;
import com.INFM255.data.MedicineView;
import com.INFM255.exception.BadRequestException;
import com.INFM255.exception.ConflictException;
import com.INFM255.repository.DiseaseRepository;
import com.INFM255.repository.MedicineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineServiceTest {

    @Mock
    private MedicineRepository medicineRepository;

    @Mock
    private DiseaseRepository diseaseRepository;

    @InjectMocks
    private MedicineService medicineService;

    @Test
    void testFindAllMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        medicines.add(createMedicineMock(1, "Aspirin", "Pain reliever", 1, BigDecimal.valueOf(10.99)));
        medicines.add(createMedicineMock(2, "Tylenol", "Fever reducer", 2, BigDecimal.valueOf(8.99)));

        List<Disease> diseases = new ArrayList<>();
        diseases.add(createDiseaseMock(1, "Cold", "Viral", "Common viral infection"));
        diseases.add(createDiseaseMock(2, "Flu", "Viral", "Influenza viral infection"));

        when(medicineRepository.findAllMedicines()).thenReturn(medicines);
        when(diseaseRepository.findDiseaseById(1)).thenReturn(diseases.get(0));
        when(diseaseRepository.findDiseaseById(2)).thenReturn(diseases.get(1));

        List<MedicineView> result = medicineService.findAllMedicines();

        assertEquals(2, result.size());
        assertEquals("Cold", result.get(0).getDisease());
        assertEquals("Flu", result.get(1).getDisease());
    }

    @Test
    void testGetPersonalMedicines() {
        Disease disease = createDiseaseMock(1, "Cold", "Viral", "Common viral infection");
        List<Disease> diseases = new ArrayList<>();
        diseases.add(disease);

        List<Medicine> medicines = new ArrayList<>();
        medicines.add(createMedicineMock(1, "Aspirin", "Pain reliever", 1, BigDecimal.valueOf(10.99)));
        medicines.add(createMedicineMock(2, "Tylenol", "Fever reducer", 1, BigDecimal.valueOf(8.99)));

        when(medicineRepository.findMedicinesByDiseaseId(disease.getId())).thenReturn(medicines);

        List<MedicineView> result = medicineService.getPersonalMedicines(diseases);

        assertEquals(2, result.size());
        assertEquals("Cold", result.get(0).getDisease());
        assertEquals("Aspirin", result.get(0).getName());
        assertEquals("Tylenol", result.get(1).getName());
    }

    @Test
    void testCreateMedicine_WithValidData() {
        Medicine medicine = new Medicine();
        medicine.setName("Aspirin");
        medicine.setDescription("Pain reliever");
        medicine.setPrice(BigDecimal.valueOf(10.99));

        Disease disease = new Disease();
        disease.setId(1);

        String diseaseName = "Cold";

        when(diseaseRepository.findDiseaseByName(diseaseName)).thenReturn(disease);
        when(medicineRepository.doesMedicineExistByName(medicine.getName())).thenReturn(false);

        assertDoesNotThrow(() -> medicineService.createMedicine(medicine, diseaseName));

        verify(medicineRepository, times(1)).createMedicine(medicine);
    }

    @Test
    void testCreateMedicine_WithEmptyName() {
        Medicine medicine = new Medicine();
        medicine.setName("");
        medicine.setDescription("Pain reliever");
        medicine.setPrice(BigDecimal.valueOf(10.99));

        String diseaseName = "Cold";

        assertThrows(BadRequestException.class, () -> medicineService.createMedicine(medicine, diseaseName));
    }

    @Test
    void testCreateMedicine_WithExistingName() {
        Medicine medicine = new Medicine();
        medicine.setName("Aspirin");
        medicine.setDescription("Pain reliever");
        medicine.setPrice(BigDecimal.valueOf(10.99));

        String diseaseName = "Cold";

        when(medicineRepository.doesMedicineExistByName(medicine.getName())).thenReturn(true);

        assertThrows(ConflictException.class, () -> medicineService.createMedicine(medicine, diseaseName));
    }

    private Medicine createMedicineMock(Integer id, String name, String description,
                                        Integer diseaseId, BigDecimal price) {
        Medicine medicine = new Medicine();
        medicine.setName(name);
        medicine.setDiseaseId(diseaseId);
        medicine.setId(id);
        medicine.setDescription(description);
        medicine.setPrice(price);
        return medicine;
    }

    private Disease createDiseaseMock(Integer id, String name, String type, String desc) {
        Disease disease = new Disease();
        disease.setDescription(desc);
        disease.setType(type);
        disease.setName(name);
        disease.setId(id);
        return disease;
    }
}
