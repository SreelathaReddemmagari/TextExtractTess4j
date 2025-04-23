package com.EcmPractise.Tessract.Repo;

import com.EcmPractise.Tessract.entity.ExtractedText;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtractedTextRepo extends JpaRepository<ExtractedText, Long> {
}
