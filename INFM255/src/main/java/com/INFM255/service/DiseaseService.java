package com.INFM255.service;

import com.INFM255.data.Disease;
import com.INFM255.data.Order;
import com.INFM255.exception.GeneralException;
import com.INFM255.repository.DiseaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;

    public List<Disease> findAllDiseases(){
        return diseaseRepository.findAllDiseases();
    }

    public void createDisease(Disease disease) {
        try {
            diseaseRepository.createDisease(disease);
        } catch (Exception exception) {
            throw new GeneralException("An error has occurred, the provided data was not registered");
        }
    }
}
