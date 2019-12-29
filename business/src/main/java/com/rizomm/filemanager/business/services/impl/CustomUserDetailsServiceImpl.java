package com.rizomm.filemanager.business.services.impl;

import com.rizomm.filemanager.business.entities.utilities.CustomUser;
import com.rizomm.filemanager.business.entities.User;
import com.rizomm.filemanager.business.repositories.UserRepository;
import com.rizomm.filemanager.business.services.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = getOptionalOfUserByUserName(username);
        return userOptional.map(CustomUser::new).get();

    }

    private Optional<User> getOptionalOfUserByUserName(String username) {

        Optional<User> userOptional = userRepository.findUserByUsername(username);
        userOptional.orElseThrow(() -> new UsernameNotFoundException("User does not exist with name " + username));
        return userOptional;
    }

}
