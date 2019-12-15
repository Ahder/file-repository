package com.rizomm.filemanager.repositories;

import com.rizomm.filemanager.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByUsername(String username);
}
