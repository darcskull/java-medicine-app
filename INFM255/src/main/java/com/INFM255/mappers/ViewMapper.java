package com.INFM255.mappers;

import com.INFM255.data.*;

public class ViewMapper {

    public static OrderView mapToOrderView(Order order, String userEmail, String medicine){
        return OrderView.builder()
                .id(order.getId())
                .price(order.getPrice())
                .address(order.getAddress())
                .number(order.getNumber())
                .phoneNumber(order.getPhoneNumber())
                .userEmail(userEmail)
                .medicine(medicine)
                .build();

    }

    public static MedicineView mapToMedicineView(Medicine medicine, String disease){
        return MedicineView.builder()
                .name(medicine.getName())
                .description(medicine.getDescription())
                .disease(disease)
                .price(medicine.getPrice())
                .build();
    }

    public static DiagnosisView mapToDiagnosisView(User user, Disease disease){
        return DiagnosisView.builder()
                .diseaseType(disease.getType())
                .diseaseDescription(disease.getDescription())
                .diseaseName(disease.getName())
                .email(user.getEmail())
                .personalNumber(user.getPersonalNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
