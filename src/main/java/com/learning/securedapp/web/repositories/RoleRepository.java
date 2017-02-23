package com.learning.securedapp.web.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.learning.securedapp.domain.Role;

/**
 * <p>RoleRepository interface.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface RoleRepository extends MongoRepository<Role, String> {

	/**
	 * <p>findByRoleName.</p>
	 *
	 * @param rolename a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.Role} object.
	 */
	Role findByRoleName(String rolename);

}
