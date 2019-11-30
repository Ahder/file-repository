package com.rizomm.filemanager.business.repositories;


import java.util.Optional;

import com.rizomm.filemanager.business.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String mail);

}
