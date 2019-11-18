package com.rizomm.filemanager.controllers;

import com.rizomm.filemanager.entites.User;
import com.rizomm.filemanager.exception.ResourceNotFoundException;
import com.rizomm.filemanager.repository.UserRepository;
import com.rizomm.filemanager.security.CurrentUser;
import com.rizomm.filemanager.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
