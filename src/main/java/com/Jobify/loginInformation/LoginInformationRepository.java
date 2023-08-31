package com.Jobify.loginInformation;

import org.springframework.data.repository.CrudRepository;

public interface LoginInformationRepository extends CrudRepository<LoginInformation, String> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword (String email, String password);
}
