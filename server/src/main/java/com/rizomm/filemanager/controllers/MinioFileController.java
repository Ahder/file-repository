package com.rizomm.filemanager.controllers;

import com.google.api.client.util.IOUtils;
import com.rizomm.filemanager.business.services.MinioService;
import com.rizomm.filemanager.business.services.UserService;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/files")
public class MinioFileController {

    @Autowired
    private UserService userService;

    @Autowired
    private MinioService minioService;

    private MinioClient minioClient;


    public MinioFileController() throws InvalidPortException, InvalidEndpointException {
    }


    @GetMapping("/getAll")
    public List<Item> getFiles(@RequestParam String bucketName) throws XmlPullParserException, InsufficientDataException, NoSuchAlgorithmException, IOException, NoResponseException, InvalidKeyException, InternalException, InvalidBucketNameException, ErrorResponseException {

        Iterable<Result<Item>> myObjects = minioClient.listObjects(bucketName);
        List<Item> items = new ArrayList<>();

        for (Result<Item> result : myObjects) {
            Item item = result.get();
            items.add(item);
        }
        return items;
    }


    /**********************************************************************************/

    @PostMapping("/createBucket/{bucketName}")
    public void createBucket(@PathVariable String bucketName, Principal principal) throws Exception {
        minioService.createBucket(bucketName, principal.getName());
    }

    @PostMapping("/uploadFile")
    public void uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String bucketName, Principal principal) throws Exception {
        minioService.uploadFile(bucketName, file, principal.getName());
    }

    @PostMapping("/uploadFiles")
    public void uploadFiles(@RequestParam("files") MultipartFile[] files, @RequestParam String bucketName, Principal principal) throws Exception {
       minioService.uploadFiles(files, bucketName, principal.getName());
    }

    @GetMapping("/downloadFile")
    public void getObject(@RequestParam Long fileId, @RequestParam String bucketName, HttpServletResponse response, Principal principal) throws Exception {
        minioService.downloadFile(bucketName, fileId, principal.getName(), response);

    }

    @DeleteMapping("/delete")
    public void deleteObject(@RequestParam Long fileId, @RequestParam String bucketName, Principal principal) throws Exception {
        minioService.deleteFile(bucketName, fileId, principal.getName());
    }







}
