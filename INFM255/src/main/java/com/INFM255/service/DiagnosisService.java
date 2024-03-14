package com.INFM255.service;

import com.INFM255.data.Diagnosis;
import com.INFM255.data.Disease;
import com.INFM255.exception.GeneralException;
import com.INFM255.repository.DiagnosisRepository;
import com.INFM255.repository.DiseaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;

    public List<Diagnosis> findAllDiagnosis(){
        return diagnosisRepository.findAllDiagnoses();
    }

    public void createDiagnosis(Diagnosis diagnosis) {
        try {
            diagnosisRepository.createDiagnosis(diagnosis);
        } catch (Exception exception) {
            throw new GeneralException("An error has occurred, the provided data was not registered");
        }
    }
}
