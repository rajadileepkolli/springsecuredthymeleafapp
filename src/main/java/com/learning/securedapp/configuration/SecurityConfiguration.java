package com.learning.securedapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.learning.securedapp.web.repositories.MongoPersistentTokenRepositoryImpl;
import com.learning.securedapp.web.repositories.RememberMeTokenRepository;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    Environment environment;
    @Autowired
    private UserDetailsService userDetailsService;
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
                userDetailsService, persistentTokenRepository());
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
            .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/resources/**",
                "/webjars/**", "/mails/**", "/**/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
            .csrf()
                .disable()
            .authorizeRequests()
                .expressionHandler(webExpressionHandler())
                .antMatchers("/forgotPwd", "/resetPwd*", "/successRegister*",
                        "/registrationConfirm*", "/registration.html", "/user/registration")
                .permitAll()
                // .antMatchers(HttpMethod.POST,"/api","/api/**").hasRole("ROLE_ADMIN")
                .anyRequest().fullyAuthenticated()
                .and()
            .formLogin()
                .loginPage("/login.html")
                .defaultSuccessUrl("/home.html")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login?error=true")
                // .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
            .sessionManagement()
                .invalidSessionUrl("/invalidSession.html")
                .sessionFixation().none()
                .and()
            .logout()
                .invalidateHttpSession(true)
                .deleteCookies("remember-me", "SESSION")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
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

}
