package com.INFM255.data;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    private String personalNumber;
    private Boolean isDoctor;

}
