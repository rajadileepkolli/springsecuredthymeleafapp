package com.learning.securedapp.web.repositories;

import com.learning.securedapp.domain.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * PermissionRepository interface.
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface PermissionRepository extends MongoRepository<Permission, String> {

    /**
     * findByName.
     *
     * @param name a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.Permission} object.
     */
    Permission findByName(String name);
}
