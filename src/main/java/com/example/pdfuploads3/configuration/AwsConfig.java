package com.example.pdfuploads3.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    private final AWSCredentials creds;

    public AwsConfig(@Value("${aws.s3.accesskey}") String awsAccessKey, @Value("${aws.s3.secretkey}") String awsSecretKey){
        creds= new BasicAWSCredentials(awsAccessKey,awsSecretKey);
    }

    @Bean
    public AmazonS3 s3Client(){
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .withRegion(Regions.AP_SOUTH_1)
                .build();
    }
}
