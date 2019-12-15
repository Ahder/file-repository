package com.rizomm.filemanager.business.services.impl;

import com.rizomm.filemanager.business.entites.MinioConnection;
import com.rizomm.filemanager.business.entites.User;
import com.rizomm.filemanager.business.repositories.UserRepository;
import com.rizomm.filemanager.business.services.UserService;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private MinioClient minioClient;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if(existingUser.isPresent()) {
            return existingUser.get();
        }
        return null;
    }

    @Override
    public User create(MinioConnection minioConnection, String email) throws InvalidPortException, InvalidEndpointException {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return null;
        }

        User user = User.builder()
                .email(email)
                .minioConnections(Stream.of(minioConnection).collect(Collectors.toList()))
                .build();

        if(null != minioConnection) {
            minioClient = new MinioClient(minioConnection.getEndPoint(), minioConnection.getAccessKey(), minioConnection.getSecretKey());
            user.setMinioConnections(Stream.of(minioConnection).collect(Collectors.toList()));
        }

        return userRepository.save(user);
    }

    @Override
    public User addConnection(MinioConnection minioConnection, String email) throws InvalidPortException, InvalidEndpointException {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (!existingUser.isPresent()) {
            return null;
        }

        minioClient = new MinioClient(minioConnection.getEndPoint(), minioConnection.getAccessKey(), minioConnection.getSecretKey());
        existingUser.get().getMinioConnections().add(minioConnection);
        return userRepository.save(existingUser.get());
    }


}
