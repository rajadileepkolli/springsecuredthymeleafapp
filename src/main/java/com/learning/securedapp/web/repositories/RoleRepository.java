package com.learning.securedapp.web.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.domain.Role;

public interface RoleRepository extends MongoRepository<Role, String>{
    Role findByRoleName(String rolename);
}
