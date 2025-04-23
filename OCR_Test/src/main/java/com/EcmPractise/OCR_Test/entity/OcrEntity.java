package com.EcmPractise.OCR_Test.entity;

public class OcrEntity {

        private String text;
        private String status;

        public OcrEntity(String text, String status) {
            this.text = text;
            this.status = status;
        }

        public String getText() {
            return text;
        }

        public String getStatus() {
            return status;
        }
    }

