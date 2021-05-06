package com.learning.securedapp.web.repositories;

import com.learning.securedapp.AbstractApplicationTests;
import com.learning.securedapp.domain.Role;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Disabled
public class RoleRepositoryTest extends AbstractApplicationTests {

    @Autowired private RoleRepository roleRepository;
    @Autowired private PermissionRepository permissionRepository;

    @Test
    public final void test() {
        List<String> roleList = new ArrayList<>();
        roleList.addAll(
                Arrays.asList("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_CMS_ADMIN", "ROLE_USER"));
        for (String string : roleList) {
            Role role = Role.builder().roleName(string).build();
            roleRepository.save(role);
        }
    }

    @Test
    public void updateRole() {
        Role role = roleRepository.findByRoleName("ROLE_SUPER_ADMIN");
        role.setPermissions(permissionRepository.findAll());
        roleRepository.save(role);
    }

    @Test
    @Disabled
    public void updateOtherRole() {
        List<String> roleList = new ArrayList<>();
        roleList.addAll(Arrays.asList("ROLE_ADMIN", "ROLE_CMS_ADMIN", "ROLE_USER"));
        for (String string : roleList) {
            Role role = roleRepository.findByRoleName(string);
            role.setPermissions(Arrays.asList(permissionRepository.findByName("MANAGE_SETTINGS")));
            roleRepository.save(role);
        }
    }
}
