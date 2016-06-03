package com.learning.securedapp.configuration;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * 
 * @author rajakolli 
 * By extending AbstractHttpSessionApplicationInitializer we ensure that
 * the Spring Bean by the name springSessionRepositoryFilter is registered with our
 * Servlet Container for every request before Spring Security’s springSecurityFilterChain
 * .
 */
public class Initializer extends AbstractHttpSessionApplicationInitializer {

}
