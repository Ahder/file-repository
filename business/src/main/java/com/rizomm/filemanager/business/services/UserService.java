package com.rizomm.filemanager.business.services;

import com.rizomm.filemanager.business.entites.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User create(String email);

/*
    User addConnection(MinioConnection minioConnection, String email) throws InvalidPortException, InvalidEndpointException;
*/
}
