package com.example.pdfuploads3.service.aws;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.concurrent.Callable;

public class S3Upload implements Callable<Boolean> {

    private final AwsService awsService;
    private final File file;
    private final String fileName;


    public S3Upload(AwsService awsService, File file,String fileName) {
        this.awsService = awsService;
        this.file=file;
        this.fileName=fileName;
    }

    @Override
    public Boolean call() {
        try {
            return awsService.uploadToS3(file, fileName);
        }catch (Exception ex){
            return false;
        }
    }
}
