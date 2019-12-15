package com.rizomm.filemanager.business.repositories;

import com.rizomm.filemanager.business.entites.MinioConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MinioConnectionRepository extends JpaRepository<MinioConnection, Long> {
}
