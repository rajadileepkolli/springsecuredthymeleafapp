package com.learning.securedapp.web.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.securedapp.domain.Permission;
import com.learning.securedapp.domain.Role;
import com.learning.securedapp.domain.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SecurityService userService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    public MyUserDetailsService() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        final String ip = getClientIP();

        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }

        try {
            User user = userService.getUserByUserName(userName);
            if (null == user) {
                throw new UsernameNotFoundException(
                        String.format("User with userName=%s was not found", userName));
            }
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(), user.getPassword(), user.isEnabled(), true, true,
                    true, getAuthorities(user.getRoleList()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roleList) {

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (Role role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            List<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                authorities
                        .add(new SimpleGrantedAuthority("ROLE_" + permission.getName()));
            }
        }
        return authorities;
    }

    private String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
