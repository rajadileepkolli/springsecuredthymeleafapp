package com.learning.securedapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;

/**
 * HierarchyRoles class.
 *
 * @author rajakolli
 * @version 1: 0
 */
@Configuration(proxyBeanMethods = false)
public class HierarchyRoles {

    /**
     * roleVoter.
     *
     * @return a {@link org.springframework.security.access.vote.RoleVoter} object.
     */
    @Bean
    public RoleVoter roleVoter() {
        return new RoleHierarchyVoter(roleHierarchy());
    }

    /**
     * roleHierarchy.
     *
     * @return a {@link org.springframework.security.access.hierarchicalroles.RoleHierarchy} object.
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String roleHierarchyStringRepresentation =
                "ROLE_SUPER_ADMIN > ROLE_ADMIN ROLE_ADMIN > ROLE_CMS_ADMIN ROLE_CMS_ADMIN > ROLE_USER";
        roleHierarchy.setHierarchy(roleHierarchyStringRepresentation);
        return roleHierarchy;
    }
}
