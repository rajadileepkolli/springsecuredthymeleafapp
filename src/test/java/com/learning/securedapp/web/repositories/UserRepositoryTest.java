package com.learning.securedapp.web.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.learning.securedapp.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DataMongoTest
class UserRepositoryTest {

    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;

    @Test
    void testSavingUser() {
        userRepository.deleteAll();
        User user =
                User.builder()
                        .userName("superadmin")
                        .password(new BCryptPasswordEncoder(10).encode("superadmin"))
                        .email("expoenviron@gmail.com")
                        .enabled(true)
                        .roleList(roleRepository.findAll())
                        .build();
        User persistedUser = userRepository.save(user);
        assertThat(persistedUser.getId()).isNotNull();
    }
}
