package com.example.pdfuploads3.service;

import com.example.pdfuploads3.model.PdfFile;
import com.example.pdfuploads3.repository.PdfFileRepository;
import com.example.pdfuploads3.service.aws.AwsService;
import com.example.pdfuploads3.service.aws.S3Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class PdfFileService {

    @Autowired
    PdfFileRepository repository;

    @Autowired
    AwsService awsService;

    public PdfFile addNewFile(String fileAddress) throws IOException {
        File file=new File(fileAddress);
        if(!validateFile(file)){
            throw new InvalidObjectException("File must be <1MB and filename contains .pdf at last");
        }
        Integer hash=generateUniqueHash(file);
        String fileName= file.getName().substring(0,file.getName().lastIndexOf("."));
        String newFileName;
        try {
            newFileName=uploadToS3(file,hash,fileName);
        } catch (Exception e) {
            throw new RuntimeException("Error while uploading to S3");
        }
        return saveToDb(fileName,newFileName,hash);
    }

    private PdfFile saveToDb(String fileName, String s3FileName, int hash){
        PdfFile pdf= new PdfFile(hash,fileName,s3FileName);
        return repository.save(pdf);
    }

    private String uploadToS3(File file, int hash, String fileName) throws ExecutionException, InterruptedException {
        String newFileName= fileName +"-"+ hash+".pdf";
        ExecutorService exec= Executors.newSingleThreadExecutor();
        Future<Boolean> uploadStatus=exec.submit(new S3Upload(awsService,file,newFileName));
        Boolean uploadResult=uploadStatus.get();
        return newFileName;
    }

    private boolean validateFile(File file) throws IOException {
        String mime=Files.probeContentType(file.toPath());
        if(!mime.equals("application/pdf"))
            return false;
        String type=file.getName().substring(file.getName().lastIndexOf(".")+1);
        if(type.isBlank()|| !type.equalsIgnoreCase("pdf"))
            return false;
        return file.length() / 1024 <= 1024 && file.length() > 0L;
    }

    private Integer generateUniqueHash(File file){
        String name=file.getName();
        int hash= Math.toIntExact((long) ((file.getAbsolutePath().hashCode())%1e6));
        List<Integer> hashes= repository.findAll().stream().map(PdfFile::getHash).collect(Collectors.toList());
        while(hashes.contains(hash)){
            hash=Math.toIntExact((long)((file.getAbsolutePath()+String.valueOf(System.currentTimeMillis())).hashCode()%1e6));
        }
        return Math.abs(hash);
    }

}
