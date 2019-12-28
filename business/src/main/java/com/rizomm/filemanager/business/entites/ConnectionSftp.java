package com.rizomm.filemanager.business.entites;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class ConnectionSftp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String host;

    private String userEmail;

    private int port = 21;
    private String directory = "/";
    private String username;
    private String password;
    
}

