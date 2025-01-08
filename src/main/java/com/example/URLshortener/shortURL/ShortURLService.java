package com.example.URLshortener.shortURL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ShortURLService {
    static final String CURRENTURL = "localhost:8080";
    @Autowired
    ShortURLRepository shortURLRepository;

    public String generateRandomURL(String originalUrl) {
        String shortCode = generateRandomShortCode(6);
        while (shortURLRepository.findByShortCode(shortCode) != null) shortCode = generateRandomShortCode(6);
        ShortURL shortURL = new ShortURL(originalUrl, shortCode);
        shortURLRepository.save(shortURL);
        return CURRENTURL + "/go/" + shortURL.getShortCode();
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

    public String generateCustomURL(String originalUrl, String customCode) {
        ShortURL shortURL = new ShortURL(originalUrl, customCode);
        shortURLRepository.save(shortURL);
        return CURRENTURL + "/go/" + shortURL.getShortCode();
    }

    public String getOriginalURL(String shortCode) {
        return shortURLRepository.findByShortCode(shortCode).getOriginalUrl();
    }

    public boolean shortCodeExists(String shortCode){
        return shortURLRepository.findByShortCode(shortCode) != null;
    }

    public Long getClickCount(String shortCode) {
        return shortURLRepository.findByShortCode(shortCode).getClickCount();
    }
}
