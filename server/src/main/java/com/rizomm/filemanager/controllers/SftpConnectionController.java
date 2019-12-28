package com.rizomm.filemanager.controllers;

import com.rizomm.filemanager.business.entites.ConnectionSftp;
import com.rizomm.filemanager.business.services.SftpConnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.Optional;


@RestController
@RequestMapping("/connection/sftp")
public class SftpConnectionController {

        @Autowired
        private SftpConnService sftpConnService;



        @PostMapping
        public ResponseEntity<ConnectionSftp> createConnectionSftp(@RequestBody @Validated ConnectionSftp connectionsftp, Principal principal) {
            connectionsftp.setUserEmail(principal.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(this.sftpConnService.create(connectionsftp));

        }

        @DeleteMapping("/{id}")
        public ResponseEntity deleteConnectionSftp(@PathVariable long id, Principal principal) {
        Optional<ConnectionSftp> ConnectionDeleteSftp = this.sftpConnService.findById(id);
        if (ConnectionDeleteSftp.isPresent()) {

        if (!ConnectionDeleteSftp.get().getUserEmail().equals(principal.getName())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        this.sftpConnService.delete(ConnectionDeleteSftp.get());
            return new ResponseEntity (HttpStatus.NO_CONTENT);
        }
            return new ResponseEntity (HttpStatus.NOT_FOUND);
}

}
