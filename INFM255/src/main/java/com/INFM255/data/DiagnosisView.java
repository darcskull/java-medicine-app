package com.INFM255.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiagnosisView {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String personalNumber;
    private String diseaseName;
    private String diseaseType;
    private String diseaseDescription;

}

