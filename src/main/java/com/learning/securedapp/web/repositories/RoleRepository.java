package com.learning.securedapp.web.repositories;

import com.learning.securedapp.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * RoleRepository interface.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface RoleRepository extends MongoRepository<Role, String> {

    /**
     * findByRoleName.
     *
     * @param rolename a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.Role} object.
     */
    Role findByRoleName(String rolename);
}
