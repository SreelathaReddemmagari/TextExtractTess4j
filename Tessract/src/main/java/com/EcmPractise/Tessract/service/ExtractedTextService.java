package com.EcmPractise.Tessract.service;

import com.EcmPractise.Tessract.Repo.ExtractedTextRepo;
import com.EcmPractise.Tessract.entity.ExtractedText;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

@Service
public class ExtractedTextService {

    @Autowired
    private ExtractedTextRepo extractedTextRepo;

    @Value("${tesseract.path}")
    private String tesseractPath;

    @Value("${tesseract.datapath}")
    private String tessdataPath;

    // Method to compress the image file into a zip file
    private File compressImageFile(File imageFile) throws IOException {
        String zipFileName = imageFile.getName().replaceAll(".jpg|.png", "") + "_compressed.zip";
        File zipFile = new File(imageFile.getParent(), zipFileName);

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            zos.putNextEntry(new ZipEntry(imageFile.getName()));

            try (FileInputStream fis = new FileInputStream(imageFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) >= 0) {
                    zos.write(buffer, 0, length);
                }
            }
            zos.closeEntry();
        }
        return zipFile;
    }

    // Method to extract text from the uploaded image
    public String extractTextFromImage(MultipartFile file) throws IOException, TesseractException {
        // Save multipart file to temporary location
        File convFile = File.createTempFile("temp", file.getOriginalFilename());
        file.transferTo(convFile);

        // Compress the image file
        File compressedFile = compressImageFile(convFile);

        // Configure Tesseract OCR
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(tessdataPath); // Path to tessdata
        tesseract.setLanguage("eng"); // Language for OCR

        // Perform OCR on the compressed file
        BufferedImage bufferedImage = ImageIO.read(compressedFile);
        String result = tesseract.doOCR(bufferedImage);

        // Save extracted text to DB
        extractedTextRepo.save(new ExtractedText(result));

        // Cleanup the files
        convFile.delete();
        compressedFile.delete();

        return result;
    }
}

