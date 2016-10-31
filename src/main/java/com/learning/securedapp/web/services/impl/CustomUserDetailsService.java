package com.learning.securedapp.web.services.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.securedapp.domain.Role;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.web.services.SecurityService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SecurityService userService;

    /**
     * <p>
     * Constructor for CustomUserDetailsService.
     * </p>
     */
    public CustomUserDetailsService() {
        super();
    }

    /** {@inheritDoc} */
    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        try {
            User user = userService.getUserByUserName(userName);
            if (null == user) {
                throw new UsernameNotFoundException(
                        String.format("User with userName=%s was not found", userName));
            }
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    user.isEnabled(),
                    true,
                    true,
                    true,
                    getAuthorities(user.getRoleList()));
        }
        catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roleList) {

        Set<GrantedAuthority> authorities = Stream
                .concat(roleList.stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName())),
                        roleList.stream().flatMap(role -> role.getPermissions().stream())
                                .map(p -> new SimpleGrantedAuthority(
                                        "ROLE_" + p.getName())))
                .collect(Collectors.toSet());

       /* for (Role role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            List<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                authorities
                        .add(new SimpleGrantedAuthority("ROLE_" + permission.getName()));
            }
        }*/
         

        return authorities;
    }

}
