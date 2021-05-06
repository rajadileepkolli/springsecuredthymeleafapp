package com.learning.securedapp.web.services.impl;

import com.learning.securedapp.domain.Permission;
import com.learning.securedapp.domain.Role;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.exception.SecuredAppException;
import com.learning.securedapp.web.repositories.PermissionRepository;
import com.learning.securedapp.web.repositories.RoleRepository;
import com.learning.securedapp.web.repositories.UserRepository;
import com.learning.securedapp.web.services.SecurityService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    /** {@inheritDoc} */
    @Override
    @Cacheable(value = "roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @Cacheable(value = "users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @CacheEvict(value = "users", allEntries = true)
    public User createUser(User user) {
        return createUser(user, true);
    }

    /** {@inheritDoc} */
    @Override
    @CacheEvict(value = "users", allEntries = true)
    public User createUser(User user, boolean validated) {
        if (!validated) {
            String email = user.getEmail();
            User userByEmail = userRepository.findByEmail(email);
            if (userByEmail != null) {
                return User.builder().build();
            }
            String password = user.getPassword();
            String encodedPwd = passwordEncoder.encode(password);
            user.setPassword(encodedPwd);
        }

        List<Role> persistedRoles =
                user.getRoleList().stream()
                        .parallel()
                        .filter(role -> role.getId() != null)
                        .map(role -> getRoleById(role.getId()))
                        .collect(Collectors.toCollection(ArrayList::new));
        user.setRoleList(persistedRoles);
        return userRepository.save(user);
    }

    /** {@inheritDoc} */
    @Override
    @Cacheable(value = "user", key = "#id")
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    /** {@inheritDoc} */
    @Override
    @Caching(
            evict = {@CacheEvict(value = "users", allEntries = true)},
            put = {@CachePut(value = "user", key = "#id")})
    public User updateUser(User user, String id) throws SecuredAppException {
        User persistedUser = getUserById(id);
        if (persistedUser == null) {
            throw new SecuredAppException("User " + user.getId() + " doesn't exist");
        }

        // List<Role> updatedRoles = new ArrayList<>();
        List<Role> updatedRoles =
                user.getRoleList().stream()
                        .parallel()
                        .filter(role -> role.getId() != null)
                        .map(role -> getRoleById(role.getId()))
                        .collect(Collectors.toCollection(ArrayList::new));
        /*
         * if(roles != null){ for (Role role : roles) { if(role.getId() != null)
         * { updatedRoles.add(getRoleById(role.getId())); } } }
         */
        persistedUser.setRoleList(updatedRoles);
        return userRepository.save(persistedUser);
    }

    /** {@inheritDoc} */
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /** {@inheritDoc} */
    @Override
    public String resetPassword(String email) throws SecuredAppException {
        User user = findUserByEmail(email);
        if (user == null) {
            throw new SecuredAppException("Invalid email address");
        }
        String uuid = UUID.randomUUID().toString();
        user.setPasswordResetToken(uuid);
        userRepository.save(user);
        return uuid;
    }

    /** {@inheritDoc} */
    @Override
    public boolean verifyPasswordResetToken(String email, String token) throws SecuredAppException {
        User user = findUserByEmail(email);
        if (user == null) {
            throw new SecuredAppException("Invalid email address");
        }
        if (!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken())) {
            return false;
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void updatePassword(String email, String token, String encodedPwd)
            throws SecuredAppException {
        User user = findUserByEmail(email);
        if (user == null) {
            throw new SecuredAppException("Invalid email address");
        }
        if (!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken())) {
            throw new SecuredAppException("Invalid password reset token");
        }
        user.setPassword(encodedPwd);
        user.setPasswordResetToken(null);
        userRepository.save(user);
    }

    /** {@inheritDoc} */
    @Override
    @Cacheable(value = "permissions")
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    /** {@inheritDoc} */
    @Override
    @CacheEvict(value = "roles", allEntries = true)
    public Role createRole(Role role) throws SecuredAppException {
        // List<Permission> persistedPermissions = new ArrayList<>();
        List<Permission> permissions =
                role.getPermissions().stream()
                        .filter(permission -> permission.getId() != null)
                        .map(permission -> findPermission(permission.getId()))
                        .collect(Collectors.toList());
        /*
         * if(permissions != null){ for (Permission permission : permissions) {
         * if(permission.getId() != null) {
         * persistedPermissions.add(findPermission(permission.getId())); } } }
         */

        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    /** {@inheritDoc} */
    @Override
    @Cacheable(value = "role", key = "#roleName", unless = "#result == null")
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    /** {@inheritDoc} */
    @Override
    @Cacheable(value = "role", key = "#id")
    public Role getRoleById(String id) {
        return roleRepository.findById(id).orElse(null);
    }

    /** {@inheritDoc} */
    @Override
    @Caching(
            evict = {@CacheEvict(value = "roles", allEntries = true)},
            put = {@CachePut(value = "role", key = "#id")})
    public Role updateRole(Role role, String id) throws SecuredAppException {
        Role persistedRole = getRoleById(id);
        if (persistedRole == null) {
            throw new SecuredAppException("Role " + id + " doesn't exist");
        }
        persistedRole.setDescription(role.getDescription());
        /*
         * List<Permission> updatedPermissions = new ArrayList<>();
         * List<Permission> permissions = role.getPermissions();
         */

        List<Permission> updatedPermissions =
                role.getPermissions().parallelStream()
                        .filter(permission -> permission.getId() != null)
                        .map(permission -> findPermission(permission.getId()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toCollection(ArrayList::new));

        /*
         * if(permissions != null){ for (Permission permission : permissions) {
         * if(permission.getId() != null) {
         * updatedPermissions.add(findPermission(permission.getId())); } } }
         */
        persistedRole.setPermissions(updatedPermissions);
        return roleRepository.save(persistedRole);
    }

    @Cacheable(value = "permission", key = "#id")
    private Permission findPermission(String id) {
        return permissionRepository.findById(id).orElse(null);
    }

    /** {@inheritDoc} */
    @Override
    @Caching(
            cacheable = {@Cacheable(value = "permission", key = "#id")},
            evict = {@CacheEvict(value = "permissions", allEntries = true)})
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    /** {@inheritDoc} */
    @Override
    @Cacheable(value = "permission", key = "#permissionName")
    public Permission getPermissionByName(String permissionName) {
        return permissionRepository.findByName(permissionName);
    }

    /** {@inheritDoc} */
    @Override
    @Cacheable(value = "user", key = "#userName", unless = "#result == null")
    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    /** {@inheritDoc} */
    @Override
    @Caching(
            evict = {
                @CacheEvict(value = "user", key = "#id"),
                @CacheEvict(value = "users", allEntries = true)
            })
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Caching(
            evict = {
                @CacheEvict(value = "role", key = "#id"),
                @CacheEvict(value = "roles", allEntries = true)
            })
    public void deleteRole(String id) {
        roleRepository.deleteById(id);
    }
}
