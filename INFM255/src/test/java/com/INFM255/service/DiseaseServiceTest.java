package com.INFM255.service;

import com.INFM255.data.Disease;
import com.INFM255.exception.BadRequestException;
import com.INFM255.exception.ConflictException;
import com.INFM255.repository.DiseaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiseaseServiceTest {

    @Mock
    private DiseaseRepository diseaseRepository;

    @InjectMocks
    private DiseaseService diseaseService;

    @Test
    void testFindAllDiseases() {
        List<Disease> diseases = new ArrayList<>();
        diseases.add(createDiseaseMock(1, "Cold", "Viral", "Common viral infection"));
        diseases.add(createDiseaseMock(2, "Flu", "Viral", "Influenza viral infection"));

        when(diseaseRepository.findAllDiseases()).thenReturn(diseases);

        List<Disease> result = diseaseService.findAllDiseases();

        assertEquals(2, result.size());
        assertEquals("Cold", result.get(0).getName());
        assertEquals("Flu", result.get(1).getName());
    }

    @Test
    void testCreateDisease_WithValidData() {
        Disease disease = new Disease();
        disease.setName("Cold");
        disease.setType("Viral");
        disease.setDescription("Common viral infection");

        when(diseaseRepository.doesDiseaseExist(disease.getName())).thenReturn(false);

        assertDoesNotThrow(() -> diseaseService.createDisease(disease));

        verify(diseaseRepository, times(1)).createDisease(disease);
    }

    @Test
    void testCreateDisease_WithEmptyName() {
        Disease disease = new Disease();
        disease.setName("");
        disease.setType("Viral");
        disease.setDescription("Common viral infection");

        assertThrows(BadRequestException.class, () -> diseaseService.createDisease(disease));
    }

    @Test
    void testCreateDisease_WithExistingName() {
        Disease disease = new Disease();
        disease.setName("Cold");
        disease.setType("Viral");
        disease.setDescription("Common viral infection");

        when(diseaseRepository.doesDiseaseExist(disease.getName())).thenReturn(true);

        assertThrows(ConflictException.class, () -> diseaseService.createDisease(disease));
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
