package com.rizomm.filemanager.business.configurations;

import com.rizomm.filemanager.business.repositories.UserRepository;
import com.rizomm.filemanager.business.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(getPasswordNoEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/users/me").access("hasAnyRole('USER','ADMIN')")
                .antMatchers("/files/**").hasRole("ADMIN")
                .antMatchers(
                        HttpMethod.GET,
                        "/swagger-ui.html"
                ).permitAll()
                .anyRequest().permitAll()
                .and()
                .httpBasic()

        ;
    }


    private PasswordEncoder getPasswordNoEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
