package com.learning.securedapp.configuration;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.header.writers.DelegatingRequestMatcherHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.learning.securedapp.web.repositories.MongoPersistentTokenRepositoryImpl;
import com.learning.securedapp.web.repositories.RememberMeTokenRepository;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    Environment environment;
    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService customUserDetailsService;
    @Autowired
    private RoleHierarchy roleHierarchy;
    @Autowired
    private RememberMeTokenRepository rememberMeTokenRepository;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        PersistentTokenBasedRememberMeServices rememberMeServices = new PersistentTokenBasedRememberMeServices(
                environment.getProperty("spring.application.name", "securedApp"),
                customUserDetailsService, persistentTokenRepository());
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        RememberMeAuthenticationProvider rememberMeAuthenticationProvider = new RememberMeAuthenticationProvider(
                environment.getProperty("spring.application.name", "securedApp"));
        return rememberMeAuthenticationProvider;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new MongoPersistentTokenRepositoryImpl(rememberMeTokenRepository);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/resources/**",
                "/webjars/**", "/mails/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        
        RequestMatcher matcher = new AntPathRequestMatcher("/login");
        DelegatingRequestMatcherHeaderWriter headerWriter =
            new DelegatingRequestMatcherHeaderWriter(matcher,new XFrameOptionsHeaderWriter());

        //to disable loading application back button after logout
        httpSecurity
            .headers()
                .defaultsDisabled()
                    .cacheControl().and()
                .contentTypeOptions().and().addHeaderWriter(headerWriter)
                .httpStrictTransportSecurity()
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000).and()
                .frameOptions().sameOrigin().xssProtection().block(false);
        
//        httpSecurity.requestCache().requestCache(new NullRequestCache());
        
        httpSecurity
            /*.csrf()
                .disable()*/
            .authorizeRequests()
                .expressionHandler(webExpressionHandler())
                .antMatchers("/forgotPwd", "/resetPwd*", "/successRegister*",
                        "/invalidSession.html", "/registrationConfirm*",
                        "/registration.html", "/user/registration", "/login*")
                .permitAll()
                // .antMatchers(HttpMethod.POST,"/api","/api/**").hasRole("ROLE_ADMIN")
                .anyRequest().fullyAuthenticated()
                .and()
            .formLogin()
                .loginPage("/login.html")
                .defaultSuccessUrl("/home.html")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login.html?error=true")
                // .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
            .sessionManagement()
                .invalidSessionUrl("/invalidSession.html")
                .sessionFixation().none()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html")
                .invalidateHttpSession(true)
                .deleteCookies("remember-me", "SESSION")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .and()
            .rememberMe()
                .rememberMeServices(rememberMeServices())
                .tokenValiditySeconds(86400)
                .rememberMeCookieName("remember-me")
                .and()
            .exceptionHandling().accessDeniedPage("/403");
    }

    private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy);
        return defaultWebSecurityExpressionHandler;
    }
    
    @Bean
    public FilterRegistrationBean getSpringSecurityFilterChainBindedToError(
                    @Qualifier("springSecurityFilterChain") Filter springSecurityFilterChain) {

            FilterRegistrationBean registration = new FilterRegistrationBean();
            registration.setFilter(springSecurityFilterChain);
            registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
            return registration;
    }

}
