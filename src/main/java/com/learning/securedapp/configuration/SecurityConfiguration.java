package com.learning.securedapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.learning.securedapp.security.MongoDBAuthenticationProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled= true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private MongoDBAuthenticationProvider authenticationProvider;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/resources/**",
                "/webjars/**","/mails/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.authorizeRequests().antMatchers("/webjars/jquery/2.2.4/jquery.min.js",
                "/webjars/bootstrap/3.3.6/css/bootstrap.min.css").permitAll();*/
        
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/forgotPwd","/resetPwd").permitAll()
                //.antMatchers(HttpMethod.POST,"/api","/api/**").hasRole("ROLE_ADMIN")
                .anyRequest().fullyAuthenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login?error")
                .permitAll()
                .and()
            .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //.logoutUrl("/logout")
                .permitAll()
                .and()
            .rememberMe()
                .and()
            .exceptionHandling().accessDeniedPage("/403");
    }
}
