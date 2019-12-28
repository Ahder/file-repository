package com.rizomm.filemanager.controllers;

import com.rizomm.filemanager.business.Utils.FileUtils;
import com.rizomm.filemanager.business.entites.ConnectionSftp;
import com.rizomm.filemanager.business.services.SftpConnService;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/connections/sftp/{connectionId}")
public class SftpUploadFileController {

    @Autowired
    private SftpConnService sftpConnService;

    private static void showServerReply(FTPClient ftp) {
        String[] replies = ftp.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile file, @PathVariable long connectionId, Principal principal) {
        Optional<ConnectionSftp> ftpConnection = sftpConnService.findById(connectionId);
        String server = ftpConnection.get().getHost();
        int port = ftpConnection.get().getPort();
        String user = ftpConnection.get().getUsername();
        String pass = ftpConnection.get().getPassword();

        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            showServerReply(ftpClient);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                throw new IOException("Exception in connecting to FTP Server");
            }
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            File fileToUpload = FileUtils.convertMultiPartToFile(file);
            InputStream inputStream = new FileInputStream(fileToUpload);
            String firstRemoteFile = file.getName();
            System.out.println("Start uploading file");

            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();

            if (done) {
                System.out.println("success :)");

                return ResponseEntity.status(HttpStatus.CREATED).body(firstRemoteFile);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();

    }
}
