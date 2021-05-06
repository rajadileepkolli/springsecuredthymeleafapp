package com.learning.securedapp.web.services;

import com.learning.securedapp.domain.Permission;
import com.learning.securedapp.domain.Role;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.exception.SecuredAppException;
import java.util.List;

public interface SecurityService {

    /**
     * getAllRoles.
     *
     * @return a {@link java.util.List} object.
     */
    List<Role> getAllRoles();

    /**
     * getAllUsers.
     *
     * @return a {@link java.util.List} object.
     */
    List<User> getAllUsers();

    /**
     * createUser.
     *
     * @param user a {@link com.learning.securedapp.domain.User} object.
     * @return a {@link com.learning.securedapp.domain.User} object.
     */
    User createUser(User user);

    /**
     * getUserById.
     *
     * @param id a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.User} object.
     */
    User getUserById(String id);

    /**
     * getUserByUserName.
     *
     * @param userName a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.User} object.
     */
    User getUserByUserName(String userName);

    /**
     * updateUser.
     *
     * @param user a {@link com.learning.securedapp.domain.User} object.
     * @param id a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.User} object.
     * @throws com.learning.securedapp.exception.SecuredAppException if any.
     */
    User updateUser(User user, String id) throws SecuredAppException;

    /**
     * findUserByEmail.
     *
     * @param email a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.User} object.
     */
    User findUserByEmail(String email);

    /**
     * resetPassword.
     *
     * @param email a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws com.learning.securedapp.exception.SecuredAppException if any.
     */
    String resetPassword(String email) throws SecuredAppException;

    /**
     * verifyPasswordResetToken.
     *
     * @param email a {@link java.lang.String} object.
     * @param token a {@link java.lang.String} object.
     * @return a boolean.
     * @throws com.learning.securedapp.exception.SecuredAppException if any.
     */
    boolean verifyPasswordResetToken(String email, String token) throws SecuredAppException;

    /**
     * updatePassword.
     *
     * @param email a {@link java.lang.String} object.
     * @param token a {@link java.lang.String} object.
     * @param encodedPwd a {@link java.lang.String} object.
     * @throws com.learning.securedapp.exception.SecuredAppException if any.
     */
    void updatePassword(String email, String token, String encodedPwd) throws SecuredAppException;

    /**
     * getAllPermissions.
     *
     * @return a {@link java.util.List} object.
     */
    List<Permission> getAllPermissions();

    /**
     * createRole.
     *
     * @param role a {@link com.learning.securedapp.domain.Role} object.
     * @return a {@link com.learning.securedapp.domain.Role} object.
     * @throws com.learning.securedapp.exception.SecuredAppException if any.
     */
    Role createRole(Role role) throws SecuredAppException;

    /**
     * getRoleById.
     *
     * @param id a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.Role} object.
     */
    Role getRoleById(String id);

    /**
     * updateRole.
     *
     * @param role a {@link com.learning.securedapp.domain.Role} object.
     * @param roleID a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.Role} object.
     * @throws com.learning.securedapp.exception.SecuredAppException if any.
     */
    Role updateRole(Role role, String roleID) throws SecuredAppException;

    /**
     * createPermission.
     *
     * @param permission a {@link com.learning.securedapp.domain.Permission} object.
     * @return a {@link com.learning.securedapp.domain.Permission} object.
     */
    Permission createPermission(Permission permission);

    /**
     * getPermissionByName.
     *
     * @param permissionName a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.Permission} object.
     */
    Permission getPermissionByName(String permissionName);

    /**
     * getRoleByName.
     *
     * @param roleName a {@link java.lang.String} object.
     * @return a {@link com.learning.securedapp.domain.Role} object.
     */
    Role getRoleByName(String roleName);

    /**
     * deleteUser.
     *
     * @param userId a {@link java.lang.String} object.
     */
    void deleteUser(String userId);

    /**
     * deleteRole.
     *
     * @param roleId a {@link java.lang.String} object.
     */
    void deleteRole(String roleId);

    /**
     * createUser.
     *
     * @param userDTO a {@link com.learning.securedapp.domain.User} object.
     * @param validates a boolean.
     * @return a {@link com.learning.securedapp.domain.User} object.
     */
    User createUser(User userDTO, boolean validates);
}
