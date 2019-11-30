package com.rizomm.filemanager.business.services;

import com.rizomm.filemanager.business.entites.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User create(User user);
}
