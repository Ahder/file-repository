package com.rizomm.filemanager.business.repositories;


import com.rizomm.filemanager.business.entites.ConnectionSftp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SftpRepository extends JpaRepository<ConnectionSftp,Long> {
}
