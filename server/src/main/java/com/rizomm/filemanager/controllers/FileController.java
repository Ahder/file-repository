package com.rizomm.filemanager.controllers;

import com.google.api.client.util.IOUtils;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private MinioClient minioClient;

    public FileController() throws InvalidPortException, InvalidEndpointException {
         minioClient = new MinioClient(" https://play.min.io", "Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG");
    }


    @PostMapping("/uploadFile")
    public void uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String bucketName) throws IOException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, InvalidArgumentException, InvalidResponseException, InternalException, NoResponseException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException {
        minioClient.putObject(bucketName,  file.getOriginalFilename() , file.getInputStream(), file.getSize(), file.getContentType());
    }

    @PostMapping("/uploadFiles")
    public void uploadFiles(@RequestParam("files") MultipartFile[] files, @RequestParam String bucketName) throws IOException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, InvalidArgumentException, InvalidResponseException, InternalException, NoResponseException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException {
        for (MultipartFile file: files) {
            minioClient.putObject(bucketName,  file.getOriginalFilename() , file.getInputStream(), file.getSize(), file.getContentType());
        }
    }

    @GetMapping("/downloadFile")
    public void getObject(@RequestParam String objectName, @RequestParam String bucketName, HttpServletResponse response) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InvalidResponseException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException {
        InputStream inputStream = minioClient.getObject(bucketName, objectName);

        // Set the content type and attachment header.
        response.addHeader("Content-disposition", "attachment;filename=" + objectName);
        response.setContentType(URLConnection.guessContentTypeFromName(objectName));

        // Copy the stream to the response's output stream.
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
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

    @DeleteMapping("/delete/{objectName}")
    public void deleteObject(@PathVariable String objectName, @RequestParam String bucketName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidResponseException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, InvalidArgumentException {
         minioClient.removeObject(bucketName,objectName);
    }

    @PostMapping("/createBucket/{bucketName}")
    public void createBucket(@PathVariable String bucketName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidResponseException, ErrorResponseException, NoResponseException, InvalidBucketNameException, XmlPullParserException, InternalException, RegionConflictException {
        minioClient.makeBucket(bucketName);
    }

}
