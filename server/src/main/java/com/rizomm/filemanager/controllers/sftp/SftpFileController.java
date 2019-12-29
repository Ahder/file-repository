package com.rizomm.filemanager.controllers.sftp;

import com.rizomm.filemanager.business.entities.ConnectionSftp;
import com.rizomm.filemanager.business.services.SftpConnService;
import com.rizomm.filemanager.business.services.Utils.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/connections/sftp/{connectionId}")
public class SftpFileController {
    String server;
    int port;
    String user;
    String pass;
    FTPClient ftpClient;

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

    @GetMapping("/downloadFile")
    public ResponseEntity<String> downloadFile(@RequestParam(value = "file") String fileName, @PathVariable long connectionId, Principal principal) {

        Optional<ConnectionSftp> ftpConnection = sftpConnService.findById(connectionId);
        server = ftpConnection.get().getHost();
        port = ftpConnection.get().getPort();
        user = ftpConnection.get().getUsername();
        pass = ftpConnection.get().getPassword();
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            String fileToDownload = fileName;
            OutputStream outputStream = new FileOutputStream(fileToDownload);
            String firstRemoteFile = fileName;
            System.out.println("Downloading file");

            boolean success = ftpClient.retrieveFile("/" + firstRemoteFile, outputStream);
            outputStream.close();

            if (success) {
                System.out.println("success :)");

                return ResponseEntity.status(HttpStatus.CREATED).body(firstRemoteFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();

    }
    
    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile file, @PathVariable long connectionId, Principal principal) {
        Optional<ConnectionSftp> ftpConnection = sftpConnService.findById(connectionId);
        server = ftpConnection.get().getHost();
        port = ftpConnection.get().getPort();
        user = ftpConnection.get().getUsername();
        pass = ftpConnection.get().getPassword();
        ftpClient = new FTPClient();

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
            System.out.println("uploading file");

            boolean success = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();

            if (success) {
                System.out.println("success :)");

                return ResponseEntity.status(HttpStatus.CREATED).body(firstRemoteFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestPart(value = "file") String file, @PathVariable long connectionId, Principal principal) {
        Optional<ConnectionSftp> ftpConnection = sftpConnService.findById(connectionId);
        server = ftpConnection.get().getHost();
        port = ftpConnection.get().getPort();
        user = ftpConnection.get().getUsername();
        pass = ftpConnection.get().getPassword();
        ftpClient = new FTPClient();

        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            String fileToDelete = file;

            boolean deleted = ftpClient.deleteFile(fileToDelete);
            if (deleted) {
                System.out.println("The file was deleted successfully.");
                ftpClient.disconnect();
            } else {
                System.out.println("Could not delete the  file.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.noContent().build();

    }
}

