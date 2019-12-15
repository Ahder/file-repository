package com.rizomm.filemanager.configurations;

import com.rizomm.filemanager.repositories.UserRepository;
import com.rizomm.filemanager.services.CustomUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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

     private CustomUserDetailsService customUserDetailsServices;

    public SecurityConfiguration(CustomUserDetailsService customUserDetailsServices) {
        this.customUserDetailsServices = customUserDetailsServices;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsServices)
                .passwordEncoder(getPasswordNoEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/file/upload").access("hasRole('USER')")
                .antMatchers("admin/files").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .csrf()
                .disable()
        ;
    }

    private PasswordEncoder getPasswordNoEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
