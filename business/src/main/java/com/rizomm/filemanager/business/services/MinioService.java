package com.rizomm.filemanager.business.services;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface MinioService {

    void createBucket(String bucketName, String email) throws Exception;

    void uploadFile(String bucketName, MultipartFile file, String email) throws Exception;

    void uploadFiles(MultipartFile[] files, String bucketName, String email) throws Exception;

    void downloadFile(String bucketName, Long fileId, String email, HttpServletResponse response) throws Exception;

    void deleteFile(String bucketName, Long fileId, String email) throws Exception;
}
