package com.learning.securedapp.web.repositories;

import com.learning.securedapp.AbstractApplicationTests;
import com.learning.securedapp.domain.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Disabled
public class PermissionRepositoryTest extends AbstractApplicationTests {

    @Autowired PermissionRepository permissionRepository;

    @Test
    public final void test() {

        List<String> permissionList = new ArrayList<>();
        permissionList.addAll(
                Arrays.asList(
                        "MANAGE_USERS", "MANAGE_ROLES", "MANAGE_PERMISSIONS", "MANAGE_SETTINGS"));
        for (String string : permissionList) {
            Permission permission = new Permission();
            permission.setName(string);
            permissionRepository.save(permission);
        }
    }
}
