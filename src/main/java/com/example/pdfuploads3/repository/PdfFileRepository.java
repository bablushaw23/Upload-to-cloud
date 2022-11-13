package com.example.pdfuploads3.repository;

import com.example.pdfuploads3.model.PdfFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PdfFileRepository extends JpaRepository<PdfFile, Integer> {

}
