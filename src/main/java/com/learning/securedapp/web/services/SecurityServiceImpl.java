package com.learning.securedapp.web.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.securedapp.domain.Role;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.exception.SecuredAppException;
import com.learning.securedapp.web.repositories.RoleRepository;
import com.learning.securedapp.web.repositories.UserRepository;

@Service
public class SecurityServiceImpl implements SecurityService{

    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;
    
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) throws SecuredAppException {
        User userByEmail = findUserByEmail(user.getEmail());
        if(userByEmail != null){
            throw new SecuredAppException("Email "+user.getEmail()+" already in use");
        }
        List<Role> persistedRoles = new ArrayList<>();
        List<Role> roles = user.getRoles();
       /* List<Role> roles = new ArrayList<>();
        Role temprole = new Role();
        temprole.setRoleName("ROLE_SUPER_ADMIN");
        temprole.setId("5752bd167df8866538ebfc8c");
        List<Permission> permissions = new ArrayList<>();
        Permission permission = new Permission();
        permission.setName("MANAGE_USERS");
        permission.setId("6");
        permissions.add(permission);
        temprole.setPermissions(permissions);
        roles.add(temprole);*/
        if(roles != null){
            for (Role role : roles) {
                if(role.getId() != null)
                {
                    persistedRoles.add(roleRepository.findOne(role.getId()));
                }
            }
        }
        user.setRoles(persistedRoles);
        
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
        List<Role> roles = user.getRoles();
        if(roles != null){
            for (Role role : roles) {
                if(role.getId() != null)
                {
                    updatedRoles.add(roleRepository.findOne(role.getId()));
                }
            }
        }
        persistedUser.setRoles(updatedRoles);
        return userRepository.save(persistedUser);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
