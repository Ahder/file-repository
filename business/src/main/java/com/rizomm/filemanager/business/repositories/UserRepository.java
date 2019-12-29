package com.rizomm.filemanager.business.repositories;

import com.rizomm.filemanager.business.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findByEmail(String mail);

}
