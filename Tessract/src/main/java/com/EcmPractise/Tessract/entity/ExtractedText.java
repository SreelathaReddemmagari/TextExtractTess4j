package com.EcmPractise.Tessract.entity;

import jakarta.persistence.*;

@Entity
public class ExtractedText {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;

    public ExtractedText() {}

    public ExtractedText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
