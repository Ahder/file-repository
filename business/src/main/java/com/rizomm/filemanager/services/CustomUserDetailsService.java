package com.rizomm.filemanager.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username);
}
