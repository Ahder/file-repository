package com.rizomm.filemanager.business.services;


import com.rizomm.filemanager.business.entites.ConnectionSftp;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface SftpConnService {

    ConnectionSftp create(ConnectionSftp Connectionsftp);
    Optional<ConnectionSftp> findById(long id);
    List<ConnectionSftp> findAll();
    void delete (ConnectionSftp connectionSftp);

}
