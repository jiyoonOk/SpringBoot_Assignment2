package kr.ac.hansung.cse.hellospringdatajpa.service;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Role;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repo.RoleRepository;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user, List<Role> userRoles) {
        for (Role ur : userRoles) {
            if (roleRepository.findByRolename(ur.getRolename()).isEmpty()) {
                roleRepository.save(ur);
            }
        }

        // 암호화 비밀번호 설정
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // 사용자 역할 설정
        user.setRoles(userRoles);

        // 유저 정보 저장
        User newUser = userRepository.save(user);

        return newUser;
    }

    public boolean checkEmailExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            return true;
        }

        return false;
    }

    public Role findByRolename(String rolename) {
        Optional<Role> role = roleRepository.findByRolename(rolename);
        return role.orElseGet(() -> new Role(rolename));
    }

}