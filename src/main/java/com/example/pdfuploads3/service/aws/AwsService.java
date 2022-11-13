package com.example.pdfuploads3.service.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URI;

@Service
public class AwsService {

    private final String s3Bucket;
    private final AmazonS3 s3Client;

    public AwsService(@Value("${aws.s3.bucket_name}") String bucketName, @Autowired AmazonS3 s3Client){
        s3Bucket=bucketName;
        this.s3Client=s3Client;
    }

    public boolean uploadToS3(File file, String fileName){
        try {
           s3Client.putObject(s3Bucket, fileName, file);
           return true;
        }catch (Exception ex){
            throw new RuntimeException("Error while uploading odf to s3");
        }
    }
}
