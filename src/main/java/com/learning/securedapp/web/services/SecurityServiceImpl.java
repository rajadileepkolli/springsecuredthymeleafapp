package com.learning.securedapp.web.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    
    @Override
    @Cacheable(value = "roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Cacheable(value = "users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @CacheEvict(value="users", allEntries = true)
    public User createUser(User user){
        return createUser(user, true);
    }
    
    @Override
    @CacheEvict(value="users", allEntries = true)
    public User createUser(User user, boolean validated) {
        if (!validated) {
            String email = user.getEmail();
            User userByEmail = userRepository.findByEmail(email);
            if (userByEmail != null) {
                return new User();
            }
            String password = user.getPassword();
            String encodedPwd = passwordEncoder.encode(password);
            user.setPassword(encodedPwd);
        }

        List<Role> persistedRoles = new ArrayList<>();
        List<Role> roles = user.getRoleList();
        if (roles != null) {
            for (Role role : roles) {
                if (role.getId() != null) {
                    persistedRoles.add(getRoleById(role.getId()));
                }
            }
        }
        user.setRoleList(persistedRoles);
        return userRepository.save(user);
    }

    @Override
    @Cacheable(value="user", key="#id")
    public User getUserById(String id) {
        return userRepository.findOne(id);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "users", allEntries = true) }, put = {
            @CachePut(value = "user", key = "#id") })
    public User updateUser(User user,String id) throws SecuredAppException {
        User persistedUser = getUserById(id);
        if(persistedUser == null){
            throw new SecuredAppException("User "+user.getId()+" doesn't exist");
        }
        
        List<Role> updatedRoles = new ArrayList<>();
        List<Role> roles = user.getRoleList();
        if(roles != null){
            for (Role role : roles) {
                if(role.getId() != null)
                {
                    updatedRoles.add(getRoleById(role.getId()));
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
    @Cacheable(value = "permissions")
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    @CacheEvict(value = "roles", allEntries = true)
    public Role createRole(Role role) throws SecuredAppException {
        List<Permission> persistedPermissions = new ArrayList<>();
        List<Permission> permissions = role.getPermissions();
        if(permissions != null){
            for (Permission permission : permissions) {
                if(permission.getId() != null)
                {
                    persistedPermissions.add(findPermission(permission.getId()));
                }
            }
        }
        
        role.setPermissions(persistedPermissions);
        return roleRepository.save(role);
    }

    @Override
    @Cacheable(value = "role", key = "#roleName", unless="#result == null")
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    @Cacheable(value = "role", key = "#id")
    public Role getRoleById(String id) {
        return roleRepository.findOne(id);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "roles", allEntries = true) }, put = {
            @CachePut(value = "role", key = "#id") })
    public Role updateRole(Role role,String id) throws SecuredAppException {
        Role persistedRole = getRoleById(id);
        if(persistedRole == null){
            throw new SecuredAppException("Role " + id + " doesn't exist");
        }
        persistedRole.setDescription(role.getDescription());
        List<Permission> updatedPermissions = new ArrayList<>();
        List<Permission> permissions = role.getPermissions();
        if(permissions != null){
            for (Permission permission : permissions) {
                if(permission.getId() != null)
                {
                    updatedPermissions.add(findPermission(permission.getId()));
                }
            }
        }
        persistedRole.setPermissions(updatedPermissions);
        return roleRepository.save(persistedRole);
    }

    @Cacheable(value = "permission", key = "#id")
    private Permission findPermission(String id) {
        return permissionRepository.findOne(id);
    }

    @Override
    @Caching(cacheable = { @Cacheable(value = "permission", key = "#id") }, evict = {
            @CacheEvict(value = "permissions", allEntries = true) })
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    @Cacheable(value="permission", key = "#permissionName")
    public Permission getPermissionByName(String permissionName) {
        return permissionRepository.findByName(permissionName);
    }

    @Override
    @Cacheable(value = "user" , key="#userName", unless = "#result == null")
    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "user", key = "#id"),
            @CacheEvict(value = "users", allEntries = true) })
    public void deleteUser(String id) {
        userRepository.delete(id);
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "role", key = "#id"),
            @CacheEvict(value = "roles", allEntries = true) })
    public void deleteRole(String id) {
        roleRepository.delete(id);
    }

}