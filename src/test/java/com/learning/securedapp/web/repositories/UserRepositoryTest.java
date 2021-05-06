package com.learning.securedapp.web.repositories;

import com.learning.securedapp.AbstractApplicationTests;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.exception.SecuredAppException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Disabled
public class UserRepositoryTest extends AbstractApplicationTests {

    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;

    @Test
    public final void test() throws SecuredAppException {
        userRepository.deleteAll();
        User user =
                User.builder()
                        .userName("superadmin")
                        .password(new BCryptPasswordEncoder(10).encode("superadmin"))
                        .email("expoenviron@gmail.com")
                        .enabled(true)
                        .roleList(roleRepository.findAll())
                        .build();
        userRepository.save(user);
    }
}
