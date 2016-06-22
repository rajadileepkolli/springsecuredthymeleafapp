package com.learning.securedapp.web.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.learning.securedapp.domain.Permission;
import com.learning.securedapp.domain.Role;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.exception.SecuredAppException;
import com.learning.securedapp.web.repositories.PermissionRepository;
import com.learning.securedapp.web.repositories.RoleRepository;
import com.learning.securedapp.web.repositories.UserRepository;

@Service
public class SecurityServiceImpl implements SecurityService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired KeyGenerator keyGenerator;
    
    @Override
    @Cacheable("roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user){
        return createUser(user, true);
    }
    
    @Override
    public User createUser(User user, boolean validated) {

        if (!validated) {
            String email = user.getEmail();
            User userByEmail = userRepository.findByEmail(email);
            if (userByEmail != null) {
                return new User();
            }
        }

        List<Role> persistedRoles = new ArrayList<>();
        List<Role> roles = user.getRoleList();
        if (roles != null) {
            for (Role role : roles) {
                if (role.getId() != null) {
                    persistedRoles.add(roleRepository.findOne(role.getId()));
                }
            }
        }
        user.setRoleList(persistedRoles);
        String password = user.getPassword();
        String encodedPwd = passwordEncoder.encode(password);
        user.setPassword(encodedPwd);

        return userRepository.save(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public User updateUser(User user) throws SecuredAppException {
        User persistedUser = getUserById(user.getId());
        if(persistedUser == null){
            throw new SecuredAppException("User "+user.getId()+" doesn't exist");
        }
        
        List<Role> updatedRoles = new ArrayList<>();
        List<Role> roles = user.getRoleList();
        if(roles != null){
            for (Role role : roles) {
                if(role.getId() != null)
                {
                    updatedRoles.add(roleRepository.findOne(role.getId()));
                }
            }
        }
        persistedUser.setRoleList(updatedRoles);
        return userRepository.save(persistedUser);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String resetPassword(String email) throws SecuredAppException {
        User user = findUserByEmail(email);
        if(user == null)
        {
            throw new SecuredAppException("Invalid email address");
        }
        String uuid = UUID.randomUUID().toString();
        user.setPasswordResetToken(uuid);
        userRepository.save(user);
        return uuid;
    }

    @Override
    public boolean verifyPasswordResetToken(String email, String token) throws SecuredAppException {
        User user = findUserByEmail(email);
        if(user == null)
        {
            throw new SecuredAppException("Invalid email address");
        }
        if(!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken())){
            return false;
        }
        return true;
    }

    @Override
    public void updatePassword(String email, String token, String encodedPwd) throws SecuredAppException {
        User user = findUserByEmail(email);
        if(user == null)
        {
            throw new SecuredAppException("Invalid email address");
        }
        if(!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken())){
            throw new SecuredAppException("Invalid password reset token");
        }
        user.setPassword(encodedPwd);
        user.setPasswordResetToken(null);
        userRepository.save(user);
    }

    @Override
    @Cacheable(value = "permissions", keyGenerator = "keyGenerator")
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public Role createRole(Role role) throws SecuredAppException {
        Role roleByName = getRoleByName(role.getRoleName());
        if(roleByName != null){
            throw new SecuredAppException("Role "+role.getRoleName()+" already exist");
        }
        List<Permission> persistedPermissions = new ArrayList<>();
        List<Permission> permissions = role.getPermissions();
        if(permissions != null){
            for (Permission permission : permissions) {
                if(permission.getId() != null)
                {
                    persistedPermissions.add(permissionRepository.findOne(permission.getId()));
                }
            }
        }
        
        role.setPermissions(persistedPermissions);
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
//    @Cacheable(value = "roles", key = "#id")
    public Role getRoleById(String id) {
        return roleRepository.findOne(id);
    }

    @Override
    public Role updateRole(Role role) throws SecuredAppException {
        Role persistedRole = getRoleById(role.getId());
        if(persistedRole == null){
            throw new SecuredAppException("Role "+role.getId()+" doesn't exist");
        }
        persistedRole.setDescription(role.getDescription());
        List<Permission> updatedPermissions = new ArrayList<>();
        List<Permission> permissions = role.getPermissions();
        if(permissions != null){
            for (Permission permission : permissions) {
                if(permission.getId() != null)
                {
                    updatedPermissions.add(permissionRepository.findOne(permission.getId()));
                }
            }
        }
        persistedRole.setPermissions(updatedPermissions);
        return roleRepository.save(persistedRole);
    }

    @Override
    @CacheEvict(value = "permissions", allEntries = true, keyGenerator = "keyGenerator")
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Permission getPermissionByName(String permissionName) {
        return permissionRepository.findByName(permissionName);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.delete(userId);
    }

    @Override
    public void deleteRole(String roleId) {
        roleRepository.delete(roleId);
    }

}
