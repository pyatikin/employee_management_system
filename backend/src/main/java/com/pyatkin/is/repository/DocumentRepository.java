package com.pyatkin.is.repository;

/**
 * todo Document type DocumentRepository
 */

import com.pyatkin.is.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}

