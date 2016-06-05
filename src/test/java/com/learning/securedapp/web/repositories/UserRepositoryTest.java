package com.learning.securedapp.web.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.learning.securedapp.ApplicationTests;
import com.learning.securedapp.domain.User;

public class UserRepositoryTest extends ApplicationTests{
    
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Test
    public final void test() {
        userRepository.deleteAll();
        User user = new User();
        user.setUserName("superadmin");
        user.setPassword(passwordEncoder.encode("superadmin"));
        user.setEmail("rajadileepkolli@gmail.com");
        user.setRoleList(roleRepository.findAll());
        userRepository.save(user);
    }

}
