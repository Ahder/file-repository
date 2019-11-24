package com.rizomm.filemanager.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class BasicConfiguration extends WebSecurityConfigurerAdapter implements ApplicationContextAware {

    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/","/hello","/login").permitAll()
                .anyRequest()
                .authenticated();
    }

}
