package com.learning.securedapp.web.services;

import java.util.List;

import com.learning.securedapp.domain.Role;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.exception.SecuredAppException;

public interface SecurityService {

    List<Role> getAllRoles();

    List<User> getAllUsers();

    User createUser(User user) throws SecuredAppException;

    User getUserById(String id);

    User updateUser(User user) throws SecuredAppException;

    User findUserByEmail(String email);

}
