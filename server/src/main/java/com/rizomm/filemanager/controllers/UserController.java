package com.rizomm.filemanager.controllers;

import com.rizomm.filemanager.business.entites.User;
import com.rizomm.filemanager.business.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/me")
    public String sayHello(Principal principal) {
        return "Hello, " + principal.getName();
    }

}
