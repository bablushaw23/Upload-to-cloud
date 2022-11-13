package com.example.pdfuploads3.controller;

import com.example.pdfuploads3.model.PdfFile;
import com.example.pdfuploads3.service.PdfFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InvalidObjectException;

@RestController
@RequestMapping("/pdf")
public class Controller {

    @Autowired
    PdfFileService service;
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PdfFile> saveNewPdf(@RequestBody String filePath) throws IOException {
        return new ResponseEntity<>(service.addNewFile(filePath), HttpStatus.CREATED);
    }

}
