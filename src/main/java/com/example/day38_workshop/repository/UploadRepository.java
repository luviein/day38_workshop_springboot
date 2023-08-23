package com.example.day38_workshop.repository;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Repository
public class UploadRepository {
    
    @Autowired
    private AmazonS3 s3;

    public String saveImage(MultipartFile uploadFile) throws IOException {
        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentType(uploadFile.getContentType());
        metaData.setContentLength(uploadFile.getSize());

        String id = UUID.randomUUID().toString().substring(0, 8);
        // puts the image in an image folder if use /image
        PutObjectRequest putRequest = new PutObjectRequest("vttpcsfbucket", id, uploadFile.getInputStream(), metaData);

        // allowing public access
        putRequest = putRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult result = s3.putObject(putRequest);
        System.out.printf(">>>>>> result: ", result);
        return id;

    }
}
