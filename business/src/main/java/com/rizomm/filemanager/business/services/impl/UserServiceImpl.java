package com.rizomm.filemanager.business.services.impl;

import java.util.List;

import com.rizomm.filemanager.business.entites.User;
import com.rizomm.filemanager.business.repositories.UserRepository;
import com.rizomm.filemanager.business.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        User save = userRepository.save(user);

        return save;
    }


}
