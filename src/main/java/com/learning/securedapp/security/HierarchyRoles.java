package com.learning.securedapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;

/**
 * <p>HierarchyRoles class.</p>
 *
 * @author rajakolli
 * @version 1: 0
 */
@Configuration
public class HierarchyRoles {

	/**
	 * <p>roleVoter.</p>
	 *
	 * @return a {@link org.springframework.security.access.vote.RoleVoter} object.
	 */
	@Bean
	public RoleVoter roleVoter() {
		return new RoleHierarchyVoter(roleHierarchy());
	}

	/**
	 * <p>roleHierarchy.</p>
	 *
	 * @return a {@link org.springframework.security.access.hierarchicalroles.RoleHierarchy} object.
	 */
	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		String roleHierarchyStringRepresentation = "ROLE_SUPER_ADMIN > ROLE_ADMIN ROLE_ADMIN > ROLE_CMS_ADMIN ROLE_CMS_ADMIN > ROLE_USER";
		roleHierarchy.setHierarchy(roleHierarchyStringRepresentation);
		return roleHierarchy;
	}
}
