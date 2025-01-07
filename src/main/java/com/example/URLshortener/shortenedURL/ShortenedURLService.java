package com.example.URLshortener.shortenedURL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ShortenedURLService {
    static final String CURRENTURL = "localhost:8080";
    @Autowired
    ShortenedURLRepository shortenedURLRepository;

    public String generateRandomURL(String originalUrl) {

        String shortCode = generateRandomShortCode(6);
        while (shortenedURLRepository.findByShortCode(shortCode) != null) shortCode = generateRandomShortCode(6);
        ShortenedURL shortenedURL = new ShortenedURL(originalUrl, shortCode);
        shortenedURLRepository.save(shortenedURL);
        System.out.println(shortenedURLRepository.findByShortCode(shortCode).getOriginalUrl());
        return CURRENTURL + "/go/" + shortenedURL.getShortCode();
    }

    public String generateRandomShortCode(int length) {
        String urlChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(urlChars.charAt(random.nextInt(urlChars.length())));
        }
        return code.toString();
    }

    public String getOriginalURL(String shortCode) {
        return shortenedURLRepository.findByShortCode(shortCode).getOriginalUrl();
    }
}
