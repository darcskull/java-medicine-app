package com.INFM255.service;

import com.INFM255.data.Disease;
import com.INFM255.exception.BadRequestException;
import com.INFM255.exception.ConflictException;
import com.INFM255.exception.GeneralException;
import com.INFM255.repository.DiseaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;

    public List<Disease> findAllDiseases(){
        return diseaseRepository.findAllDiseases();
    }

    public void createDisease(Disease disease) {

        if (Objects.equals(disease.getName(), "") || Objects.equals(disease.getType(), "") ||
                Objects.equals(disease.getDescription(), "")) {
            throw new BadRequestException("Empty values are not allowed");
        }

        if(diseaseRepository.doesDiseaseExist(disease.getName())){
            throw new ConflictException("The disease is already registered");
        }

        try {
            diseaseRepository.createDisease(disease);
        } catch (Exception exception) {
            throw new GeneralException("An error has occurred, the provided data was not registered");
        }
    }
}
