package com.example.URLshortener.shortenedURL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortenedURLRepository extends JpaRepository<ShortenedURL,Long> {
}
