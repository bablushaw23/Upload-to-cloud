package com.example.pdfuploads3.model;

import javax.persistence.*;

@Entity
public class PdfFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileId;

    @Column(unique = true)
    private Integer hash;

    private String fileName;

    private String s3FileName;

    public PdfFile() {
    }

    public PdfFile(Integer hash, String fileName, String s3FileName) {
        this.hash = hash;
        this.fileName = fileName;
        this.s3FileName = s3FileName;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getHash() {
        return hash;
    }

    public void setHash(Integer hash) {
        this.hash = hash;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getS3FileName() {
        return s3FileName;
    }

    public void setS3FileName(String s3FileName) {
        this.s3FileName = s3FileName;
    }
}
