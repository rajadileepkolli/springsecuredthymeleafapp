package com.learning.securedapp.web.services;

import java.util.List;

import com.learning.securedapp.domain.Permission;
import com.learning.securedapp.domain.Role;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.exception.SecuredAppException;

/**
 * <p>SecurityService interface.</p>
 *
 * @author rajakolli
 * @version $Id: $Id
 */
public interface SecurityService {

	/**
	 * <p>getAllRoles.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<Role> getAllRoles();

	/**
	 * <p>getAllUsers.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<User> getAllUsers();

	/**
	 * <p>createUser.</p>
	 *
	 * @param user a {@link com.learning.securedapp.domain.User} object.
	 * @return a {@link com.learning.securedapp.domain.User} object.
	 */
	User createUser(User user);

	/**
	 * <p>getUserById.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.User} object.
	 */
	User getUserById(String id);

	/**
	 * <p>getUserByUserName.</p>
	 *
	 * @param userName a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.User} object.
	 */
	User getUserByUserName(String userName);

	/**
	 * <p>updateUser.</p>
	 *
	 * @param user a {@link com.learning.securedapp.domain.User} object.
	 * @param id a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.User} object.
	 * @throws com.learning.securedapp.exception.SecuredAppException if any.
	 */
	User updateUser(User user, String id) throws SecuredAppException;

	/**
	 * <p>findUserByEmail.</p>
	 *
	 * @param email a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.User} object.
	 */
	User findUserByEmail(String email);

	/**
	 * <p>resetPassword.</p>
	 *
	 * @param email a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 * @throws com.learning.securedapp.exception.SecuredAppException if any.
	 */
	String resetPassword(String email) throws SecuredAppException;

	/**
	 * <p>verifyPasswordResetToken.</p>
	 *
	 * @param email a {@link java.lang.String} object.
	 * @param token a {@link java.lang.String} object.
	 * @return a boolean.
	 * @throws com.learning.securedapp.exception.SecuredAppException if any.
	 */
	boolean verifyPasswordResetToken(String email, String token) throws SecuredAppException;

	/**
	 * <p>updatePassword.</p>
	 *
	 * @param email a {@link java.lang.String} object.
	 * @param token a {@link java.lang.String} object.
	 * @param encodedPwd a {@link java.lang.String} object.
	 * @throws com.learning.securedapp.exception.SecuredAppException if any.
	 */
	void updatePassword(String email, String token, String encodedPwd) throws SecuredAppException;

	/**
	 * <p>getAllPermissions.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<Permission> getAllPermissions();

	/**
	 * <p>createRole.</p>
	 *
	 * @param role a {@link com.learning.securedapp.domain.Role} object.
	 * @return a {@link com.learning.securedapp.domain.Role} object.
	 * @throws com.learning.securedapp.exception.SecuredAppException if any.
	 */
	Role createRole(Role role) throws SecuredAppException;

	/**
	 * <p>getRoleById.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.Role} object.
	 */
	Role getRoleById(String id);

	/**
	 * <p>updateRole.</p>
	 *
	 * @param role a {@link com.learning.securedapp.domain.Role} object.
	 * @param roleID a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.Role} object.
	 * @throws com.learning.securedapp.exception.SecuredAppException if any.
	 */
	Role updateRole(Role role, String roleID) throws SecuredAppException;

	/**
	 * <p>createPermission.</p>
	 *
	 * @param permission a {@link com.learning.securedapp.domain.Permission} object.
	 * @return a {@link com.learning.securedapp.domain.Permission} object.
	 */
	Permission createPermission(Permission permission);

	/**
	 * <p>getPermissionByName.</p>
	 *
	 * @param permissionName a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.Permission} object.
	 */
	Permission getPermissionByName(String permissionName);

	/**
	 * <p>getRoleByName.</p>
	 *
	 * @param roleName a {@link java.lang.String} object.
	 * @return a {@link com.learning.securedapp.domain.Role} object.
	 */
	Role getRoleByName(String roleName);

	/**
	 * <p>deleteUser.</p>
	 *
	 * @param userId a {@link java.lang.String} object.
	 */
	void deleteUser(String userId);

	/**
	 * <p>deleteRole.</p>
	 *
	 * @param roleId a {@link java.lang.String} object.
	 */
	void deleteRole(String roleId);

	/**
	 * <p>createUser.</p>
	 *
	 * @param userDTO a {@link com.learning.securedapp.domain.User} object.
	 * @param validates a boolean.
	 * @return a {@link com.learning.securedapp.domain.User} object.
	 */
	User createUser(User userDTO, boolean validates);

}
