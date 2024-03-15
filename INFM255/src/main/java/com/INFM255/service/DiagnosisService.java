package com.INFM255.service;

import com.INFM255.data.Diagnosis;
import com.INFM255.data.DiagnosisView;
import com.INFM255.data.Disease;
import com.INFM255.data.User;
import com.INFM255.exception.ConflictException;
import com.INFM255.exception.GeneralException;
import com.INFM255.repository.DiagnosisRepository;
import com.INFM255.repository.DiseaseRepository;
import com.INFM255.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.INFM255.mappers.ViewMapper.mapToDiagnosisView;

@Service
@RequiredArgsConstructor
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final UserRepository userRepository;
    private final DiseaseRepository diseaseRepository;

    public List<DiagnosisView> findAllDiagnosis() {
        List<Diagnosis> diagnosisList = diagnosisRepository.findAllDiagnoses();
        List<DiagnosisView> viewList = new ArrayList<>();
        for (Diagnosis diagnosis : diagnosisList) {
            DiagnosisView view = mapToDiagnosisView(userRepository.findUserById(diagnosis.getUserId()),
                    diseaseRepository.findDiseaseById(diagnosis.getDiseaseId()));
            viewList.add(view);
        }
        return viewList;
    }

    public List<Disease> findPersonalDiseases(Integer userId){
        List<Diagnosis> diagnosisList = diagnosisRepository.findDiagnosesByUserId(userId);
        List<Disease> diseases = new ArrayList<>();
        for(Diagnosis diagnosis: diagnosisList){
            Disease newDisease = diseaseRepository.findDiseaseById(diagnosis.getDiseaseId());
            diseases.add(newDisease);
        }

        return diseases;
    }

    public void createDiagnosis(String email, String diseaseName) {
        User user = userRepository.findUserByEmail(email);
        Disease disease = diseaseRepository.findDiseaseByName(diseaseName);

        if (diagnosisRepository.checkExistence(user.getId(), disease.getId())) {
            throw new ConflictException("The record already exist");
        }
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiseaseId(disease.getId());
        diagnosis.setUserId(user.getId());
        createDiagnosis(diagnosis);

    }

    private void createDiagnosis(Diagnosis diagnosis) {
        try {
            diagnosisRepository.createDiagnosis(diagnosis);
        } catch (Exception exception) {
            throw new GeneralException("An error has occurred, the provided data was not registered");
        }
    }
}
