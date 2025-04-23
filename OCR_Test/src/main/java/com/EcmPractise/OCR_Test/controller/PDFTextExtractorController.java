package com.EcmPractise.OCR_Test.controller;

import com.EcmPractise.OCR_Test.service.PDFTextExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pdf")
public class PDFTextExtractorController {
    @Autowired
    private PDFTextExtractorService pdfTextExtractorService;

    @PostMapping("/upload")
    public ResponseEntity<String> extractTextFromPDF(@RequestParam("file") MultipartFile file) {
        try {
            String extractedText = pdfTextExtractorService.extractTextFromPDF(String.valueOf(file));
            return ResponseEntity.ok(extractedText);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error extracting text: " + e.getMessage());
        }
    }
    }

