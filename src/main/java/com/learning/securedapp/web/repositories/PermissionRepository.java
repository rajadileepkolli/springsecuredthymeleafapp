package com.learning.securedapp.web.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.domain.Permission;

/**
 * <p>PermissionRepository interface.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface PermissionRepository extends MongoRepository<Permission, String> {

	/**
	 * <p>findByName.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.Permission} object.
	 */
	Permission findByName(String name);

}
