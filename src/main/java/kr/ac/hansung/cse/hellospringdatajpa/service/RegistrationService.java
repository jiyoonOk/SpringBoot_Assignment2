package kr.ac.hansung.cse.hellospringdatajpa.service;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Role;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;

import java.util.List;

public interface RegistrationService {
    User createUser(User user, List<Role> userRoles);

    boolean checkEmailExists(String email);

    Role findByRolename(String rolename);
}