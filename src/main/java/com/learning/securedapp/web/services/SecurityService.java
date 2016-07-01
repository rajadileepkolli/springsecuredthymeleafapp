package com.learning.securedapp.web.services;

import java.util.List;

import com.learning.securedapp.domain.Permission;
import com.learning.securedapp.domain.Role;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.exception.SecuredAppException;

public interface SecurityService {

    List<Role> getAllRoles();

    List<User> getAllUsers();

    User createUser(User user);

    User getUserById(String id);
    
    User getUserByUserName(String userName);

    User updateUser(User user, String id) throws SecuredAppException;

    User findUserByEmail(String email);

    String resetPassword(String email) throws SecuredAppException;

    boolean verifyPasswordResetToken(String email, String token) throws SecuredAppException;

    void updatePassword(String email, String token, String encodedPwd) throws SecuredAppException;

    List<Permission> getAllPermissions();

    Role createRole(Role role) throws SecuredAppException;

    Role getRoleById(String id);

    Role updateRole(Role role, String roleID) throws SecuredAppException;

    Permission createPermission(Permission permission);

    Permission getPermissionByName(String permissionName);

    Role getRoleByName(String roleName);

    void deleteUser(String userId);

    void deleteRole(String roleId);

    User createUser(User userDTO, boolean validates);

}
