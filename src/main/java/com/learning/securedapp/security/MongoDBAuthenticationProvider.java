package com.learning.securedapp.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.learning.securedapp.domain.Permission;
import com.learning.securedapp.domain.Role;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.web.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor // This is used to autowire userRepository bean from spring boot 1.4.0
public class MongoDBAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private UserRepository userRepository;
    
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication)
                    throws AuthenticationException {/*
        if (!passwordEncoder.encode((String) authentication.getCredentials()).equals(userDetails.getPassword())) {
            throw new InternalAuthenticationServiceException(
                    "UserDetails didn't matched, which is an interface contract violation");
        }
    */}

    @Override
    protected UserDetails retrieveUser(String username,
            UsernamePasswordAuthenticationToken authentication)
                    throws AuthenticationException {

        UserDetails loadedUser = null;

        try {
            User dbUser = userRepository.findByUserName(username);
            if (dbUser != null) {
                loadedUser = new org.springframework.security.core.userdetails.User(
                        dbUser.getUserName(), dbUser.getPassword(),
                        convertToGrantedAuthority(dbUser.getRoles()));
            }
        } catch (Exception repositoryProblem) {
            throw new InternalAuthenticationServiceException(
                    repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }

        return loadedUser;
    }

    private Set<GrantedAuthority> convertToGrantedAuthority(List<Role> roles) {

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            List<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                authorities
                        .add(new SimpleGrantedAuthority("ROLE_" + permission.getName()));
            }
        }
        return authorities;
    }

}
