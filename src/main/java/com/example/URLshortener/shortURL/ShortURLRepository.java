package com.example.URLshortener.shortURL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortURLRepository extends JpaRepository<ShortURL,Long> {
    ShortURL findByShortCode(String shortCode);
}
