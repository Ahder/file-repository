package com.rizomm.filemanager.business.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(
                                        "/swagger-ui.html",
                                        "/v2/api-docs",
                                        "/webjars/**",
                                        "/swagger-resources/**",
                                        "/",
                                        "/users").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer()
                .jwt();
    }
}
