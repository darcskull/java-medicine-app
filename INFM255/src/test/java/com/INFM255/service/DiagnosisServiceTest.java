package com.INFM255.service;

import com.INFM255.data.Diagnosis;
import com.INFM255.data.DiagnosisView;
import com.INFM255.data.Disease;
import com.INFM255.data.User;
import com.INFM255.exception.ConflictException;
import com.INFM255.repository.DiagnosisRepository;
import com.INFM255.repository.DiseaseRepository;
import com.INFM255.repository.UserRepository;
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
class DiagnosisServiceTest {

    @Mock
    private DiagnosisRepository diagnosisRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DiseaseRepository diseaseRepository;

    @InjectMocks
    private DiagnosisService diagnosisService;

    @Test
    void testFindAllDiagnosis() {
        List<Diagnosis> diagnosisList = new ArrayList<>();
        diagnosisList.add(createDiagnosisMock(1, 1, 1));
        diagnosisList.add(createDiagnosisMock(2, 2, 2));

        when(diagnosisRepository.findAllDiagnoses()).thenReturn(diagnosisList);
        when(userRepository.findUserById(anyInt())).thenReturn(new User());
        when(diseaseRepository.findDiseaseById(anyInt())).thenReturn(new Disease());

        List<DiagnosisView> result = diagnosisService.findAllDiagnosis();

        assertEquals(2, result.size());
    }

    @Test
    void testFindPersonalDiseases() {
        int userId = 1;
        List<Diagnosis> diagnosisList = new ArrayList<>();
        diagnosisList.add(createDiagnosisMock(1, userId, 1));
        diagnosisList.add(createDiagnosisMock(2, userId, 2));

        when(diagnosisRepository.findDiagnosesByUserId(userId)).thenReturn(diagnosisList);
        when(diseaseRepository.findDiseaseById(anyInt())).thenReturn(new Disease());

        List<Disease> result = diagnosisService.findPersonalDiseases(userId);

        assertEquals(2, result.size());
    }

    @Test
    void testCreateDiagnosis_WithValidData() {
        String email = "user@example.com";
        String diseaseName = "Cold";

        User user = new User();
        user.setId(1);

        Disease disease = new Disease();
        disease.setId(1);

        when(userRepository.findUserByEmail(email)).thenReturn(user);
        when(diseaseRepository.findDiseaseByName(diseaseName)).thenReturn(disease);
        when(diagnosisRepository.checkExistence(user.getId(), disease.getId())).thenReturn(false);

        assertDoesNotThrow(() -> diagnosisService.createDiagnosis(email, diseaseName));

        verify(diagnosisRepository, times(1)).createDiagnosis(any());
    }

    @Test
    void testCreateDiagnosis_WithExistingRecord() {
        String email = "user@example.com";
        String diseaseName = "Cold";

        User user = new User();
        user.setId(1);

        Disease disease = new Disease();
        disease.setId(1);

        when(userRepository.findUserByEmail(email)).thenReturn(user);
        when(diseaseRepository.findDiseaseByName(diseaseName)).thenReturn(disease);
        when(diagnosisRepository.checkExistence(user.getId(), disease.getId())).thenReturn(true);

        assertThrows(ConflictException.class, () -> diagnosisService.createDiagnosis(email, diseaseName));
    }

    private Diagnosis createDiagnosisMock(Integer id, Integer userId, Integer disId){
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setUserId(userId);
        diagnosis.setId(id);
        diagnosis.setDiseaseId(disId);
        return diagnosis;
    }
}
