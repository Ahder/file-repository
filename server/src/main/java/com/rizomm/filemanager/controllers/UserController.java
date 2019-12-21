package com.rizomm.filemanager.controllers;

import com.rizomm.filemanager.business.entites.User;
import com.rizomm.filemanager.business.services.UserService;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/me")
    public String sayHello(Principal principal) {
        return "Hello, " + principal.getName();
    }

    @PostMapping("/createUser")
    public ResponseEntity createUser(Principal principal) throws InvalidPortException, InvalidEndpointException {
        User createdUser = userService.create(principal.getName());
        if (createdUser != null) {
            return new ResponseEntity(createdUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity("User " + principal.getName() + " already exists", HttpStatus.BAD_REQUEST);
        }
    }
/*
    @PostMapping("/addConnection")
    public ResponseEntity addConnection(@RequestBody MinioConnection minioConnection, Principal principal) throws InvalidPortException, InvalidEndpointException {
        User existingUser = userService.addConnection(minioConnection, principal.getName());
        if (existingUser != null) {
            return new ResponseEntity(existingUser, HttpStatus.OK);
        } else {
            return new ResponseEntity("User " + principal.getName() + " does not exist", HttpStatus.NOT_FOUND);
        }
    }*/
}
