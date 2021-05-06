package com.learning.securedapp.web.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.learning.securedapp.domain.Permission;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
class PermissionRepositoryTest {

    @Autowired private PermissionRepository permissionRepository;

    @Test
    void test() {

        List<String> permissionList =
                new ArrayList<>(
                        List.of(
                                "MANAGE_USERS",
                                "MANAGE_ROLES",
                                "MANAGE_PERMISSIONS",
                                "MANAGE_SETTINGS"));
        for (String string : permissionList) {
            Permission permission = new Permission();
            permission.setName(string);
            permissionRepository.save(permission);
        }
        assertThat(this.permissionRepository.count()).isEqualTo(4);
    }
}
