package com.rizomm.filemanager.business.services.impl;


import com.rizomm.filemanager.business.entites.ConnectionSftp;
import com.rizomm.filemanager.business.repositories.SftpRepository;
import com.rizomm.filemanager.business.services.SftpConnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class SftpConnServiceImpl implements SftpConnService {

    @Autowired
    private SftpRepository sftpConnectionRepository;

    @Override
    public ConnectionSftp create(ConnectionSftp Connectionsftp) {

        return sftpConnectionRepository.save(Connectionsftp);
    }
    @Override
    public Optional<ConnectionSftp> findById (long id) {

        return sftpConnectionRepository.findById(id);
    }

    @Override
    public List<ConnectionSftp> findAll() {

        return sftpConnectionRepository.findAll();
    }

    @Override
    public void delete(ConnectionSftp connectionSftp) {

        sftpConnectionRepository.delete(connectionSftp);
    }

}
