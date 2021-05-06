package com.learning.securedapp.web.services.impl;

import static java.util.stream.Collectors.toSet;

import com.learning.securedapp.domain.Permission;
import com.learning.securedapp.domain.Role;
import com.learning.securedapp.domain.User;
import com.learning.securedapp.web.services.SecurityService;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SecurityService userService;

    /** {@inheritDoc} */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
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

    private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roleList) {
        Stream<SimpleGrantedAuthority> roleStream =
                roleList.stream().map(Role::getRoleName).map(SimpleGrantedAuthority::new);
        Stream<SimpleGrantedAuthority> permissionStream =
                roleList.stream()
                        .map(Role::getPermissions)
                        .filter(Objects::nonNull)
                        .flatMap(Collection::stream)
                        .map(Permission::getName)
                        .filter(Objects::nonNull)
                        .map(
                                permissionName ->
                                        new SimpleGrantedAuthority("ROLE_" + permissionName));
        return Stream.concat(roleStream, permissionStream).collect(toSet());
    }
}
