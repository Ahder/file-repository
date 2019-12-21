package com.rizomm.filemanager.business.repositories;

import com.rizomm.filemanager.business.entites.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

}
