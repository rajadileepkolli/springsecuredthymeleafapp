package com.learning.securedapp.configuration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityInitializer() {
        super(SecurityConfiguration.class, RedisConfiguration.class);
    }

    /*
     * Our Spring Configuration created a Spring Bean named springSessionRepositoryFilter
     * that implements Filter. The springSessionRepositoryFilter bean is responsible for
     * replacing the HttpSession with a custom implementation that is backed by Spring
     * Session.
     * 
     * In order for our Filter to do its magic, Spring needs to load our
     * RedisConfiguration class. Since our application is already loading Spring
     * configuration using our SecurityInitializer class, we can simply add our
     * RedisConfiguration class to it.
     */
}
