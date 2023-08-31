package com.Jobify.StudentProfileInformation;

import com.Jobify.loginInformation.LoginInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentProfileInformationService {

    @Autowired
    private StudentProfileInformationRepository studentProfileInformationRepository;

    public List<StudentProfileInformation> getAllStudents() {
        List<StudentProfileInformation> studentProfileInformation = new ArrayList<>();
        studentProfileInformationRepository.findAll().forEach(studentProfileInformation::add);
        return studentProfileInformation;
    }
    public StudentProfileInformation getStudent(String email) {
        return studentProfileInformationRepository.findById(email).get();
    }
    public void addStudent(StudentProfileInformation studentProfileInformation) {
        studentProfileInformationRepository.save(studentProfileInformation);
    }

    public void updateStudent(StudentProfileInformation studentProfileInformation) {
        studentProfileInformationRepository.save(studentProfileInformation);
    }

    public void deleteStudent(String email) {
        studentProfileInformationRepository.deleteById(email);
    }
}
