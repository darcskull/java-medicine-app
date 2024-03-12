package com.INFM255.service;

import com.INFM255.data.User;
import com.INFM255.exception.NotFoundException;
import com.INFM255.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public User verifyLogin(String email, String password) {
        User loggedUser = userRepository.findUserByEmailAndPassword(email, password);
        if (loggedUser == null) {
            throw new NotFoundException("User was not found");
        }

        return loggedUser;

    }
}
