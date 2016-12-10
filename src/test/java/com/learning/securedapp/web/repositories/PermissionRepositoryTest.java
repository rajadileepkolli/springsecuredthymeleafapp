package com.learning.securedapp.web.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.learning.securedapp.configuration.MongoDBConfiguration;
import com.learning.securedapp.domain.Permission;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={MongoDBConfiguration.class})
@ActiveProfiles(value = "test")
public class PermissionRepositoryTest {

	@Autowired
	PermissionRepository permissionRepository;

	@Test
	public final void test() {

		List<String> permissionList = new ArrayList<>();
		permissionList.addAll(Arrays.asList("MANAGE_USERS", "MANAGE_ROLES", "MANAGE_PERMISSIONS", "MANAGE_SETTINGS"));
		for (String string : permissionList) {
			Permission permission = new Permission();
			permission.setName(string);
			permissionRepository.save(permission);
		}
	}

}
