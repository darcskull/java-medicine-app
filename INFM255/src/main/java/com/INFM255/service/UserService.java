package com.INFM255.service;

import com.INFM255.data.User;
import com.INFM255.exception.GeneralException;
import com.INFM255.exception.NotFoundException;
import com.INFM255.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllPatients() {
        return userRepository.findAllUsers(false);
    }

    public List<User> getAllDoctors() {
        return userRepository.findAllUsers(true);
    }

    public User verifyLogin(String email, String password) {
        User loggedUser = userRepository.findUserByEmailAndPassword(email, password);
        if (loggedUser == null) {
            throw new NotFoundException("User was not found");
        }

        return loggedUser;

    }

    public void registerUser(User userData) {
        if (userRepository.doesUserExistByEmail(userData.getEmail())) {
            throw new GeneralException("The email is already registered");
        }

        if(!isValidPhoneNumber(userData.getPhoneNumber())){
            throw new GeneralException("The provided phone number is invalid");
        }
        try {
            userRepository.createUser(userData);
        }catch (Exception exception){
            throw new GeneralException("An error has occurred, the provided data was not registered");
        }

    }


    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "08\\d{8}";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }
}
