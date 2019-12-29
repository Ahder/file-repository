package com.rizomm.filemanager.business.services.impl;

import com.rizomm.filemanager.business.entities.User;
import com.rizomm.filemanager.business.repositories.UserRepository;
import com.rizomm.filemanager.business.services.UserService;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User create(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return null;
        }

        User user = User.builder()
                .email(email)
                .build();

        return userRepository.save(user);
    }

/*    @Override
    public User addConnection(MinioConnection minioConnection, String email) throws InvalidPortException, InvalidEndpointException {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (!existingUser.isPresent()) {
            return null;
        }

        minioClient = new MinioClient(minioConnection.getEndPoint(), minioConnection.getAccessKey(), minioConnection.getSecretKey());
        existingUser.get().getMinioConnections().add(minioConnection);
        return userRepository.save(existingUser.get());
    }*/


}
