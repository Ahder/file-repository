package com.rizomm.filemanager.business.services;

import com.rizomm.filemanager.business.entites.MinioConnection;
import com.rizomm.filemanager.business.entites.User;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    User findByEmail(String email);

    User create(MinioConnection minioConnection, String email) throws InvalidPortException, InvalidEndpointException;

    User addConnection(MinioConnection minioConnection, String email) throws InvalidPortException, InvalidEndpointException;
}
