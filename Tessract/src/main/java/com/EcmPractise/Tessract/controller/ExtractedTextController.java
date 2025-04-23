package com.EcmPractise.Tessract.controller;

import com.EcmPractise.Tessract.service.ExtractedTextService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ocr")
public class ExtractedTextController {
    @Autowired
    private ExtractedTextService extractedTextService;
    @PostMapping("/extract")
    public ResponseEntity<String> extractText(@RequestParam("image") MultipartFile file) {
        try {
            String text = extractedTextService.extractTextFromImage(file);
            return ResponseEntity.ok(text);
        } catch ( IOException | TesseractException e) {
            return ResponseEntity.internalServerError().body("Error extracting text: " + e.getMessage());
        }
    }
}

