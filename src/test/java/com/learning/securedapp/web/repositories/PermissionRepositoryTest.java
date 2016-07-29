package com.learning.securedapp.web.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.learning.securedapp.ApplicationTests;
import com.learning.securedapp.domain.Permission;

public class PermissionRepositoryTest extends ApplicationTests {

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
