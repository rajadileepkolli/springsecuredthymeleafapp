package com.learning.securedapp.web.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.domain.Permission;

public interface PermissionRepository extends MongoRepository<Permission, String> {

	Permission findByName(String name);

}
