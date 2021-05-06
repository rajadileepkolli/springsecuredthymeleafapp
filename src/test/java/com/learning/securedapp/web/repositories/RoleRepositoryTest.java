package com.learning.securedapp.web.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.learning.securedapp.domain.Role;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
class RoleRepositoryTest {

    @Autowired private RoleRepository roleRepository;
    @Autowired private PermissionRepository permissionRepository;

    @Test
    void test() {
        List<String> roleList =
                new ArrayList<>(
                        List.of("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_CMS_ADMIN", "ROLE_USER"));
        for (String string : roleList) {
            Role role = Role.builder().roleName(string).build();
            roleRepository.save(role);
        }
        assertThat(this.roleRepository.count()).isEqualTo(4);
    }

    @Test
    void updateRole() {
        Role role = roleRepository.findByRoleName("ROLE_SUPER_ADMIN");
        role.setPermissions(permissionRepository.findAll());
        roleRepository.save(role);
        assertThat(this.roleRepository.findByRoleName("ROLE_SUPER_ADMIN").getPermissions())
                .isNotEmpty()
                .hasSize(4);
    }

    @Test
    void updateOtherRole() {
        List<String> roleList =
                new ArrayList<>(Arrays.asList("ROLE_ADMIN", "ROLE_CMS_ADMIN", "ROLE_USER"));
        for (String string : roleList) {
            Role role = roleRepository.findByRoleName(string);
            role.setPermissions(
                    Collections.singletonList(permissionRepository.findByName("MANAGE_SETTINGS")));
            roleRepository.save(role);
        }
    }
}
