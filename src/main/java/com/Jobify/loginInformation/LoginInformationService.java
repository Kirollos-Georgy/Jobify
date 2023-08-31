package com.Jobify.loginInformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginInformationService {

    @Autowired
    private LoginInformationRepository loginInformationRepository;

    public List<LoginInformation> getAllUsers() {
       List<LoginInformation> loginInformation = new ArrayList<>();
       loginInformationRepository.findAll().forEach(loginInformation::add);
       return loginInformation;
    }
    public LoginInformation getUser(String email) {
        return loginInformationRepository.findById(email).get();
    }
    public void addUser(LoginInformation loginInformation) {
        loginInformationRepository.save(loginInformation);
    }

    public void updateUser(LoginInformation loginInformation) {
        loginInformationRepository.save(loginInformation);
    }

    public void deleteUser(String email) {
        loginInformationRepository.deleteById(email);
    }

    public boolean emailUsed(String email) {
        return loginInformationRepository.existsByEmail(email);
    }

    public boolean validLoginInformation(LoginInformation loginInformation) {
        return loginInformationRepository.existsByEmailAndPassword(loginInformation.getEmail(), loginInformation.getPassword());
    }
}
