package com.example.UrlShortener.shortUrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ShortUrlService {
    static final String CURRENTURL = "localhost:8080";
    @Autowired
    ShortUrlRepository shortUrlRepository;

    public String generateRandomUrl(String originalUrl) {
        String shortCode = generateRandomShortCode(6);
        while (shortUrlRepository.findByShortCode(shortCode) != null) shortCode = generateRandomShortCode(6);
        ShortUrl shortUrl = new ShortUrl(originalUrl, shortCode);
        shortUrlRepository.save(shortUrl);
        return CURRENTURL + "/go/" + shortUrl.getShortCode();
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

    public String generateCustomUrl(String originalUrl, String customCode) {
        ShortUrl shortUrl = new ShortUrl(originalUrl, customCode);
        shortUrlRepository.save(shortUrl);
        return CURRENTURL + "/go/" + shortUrl.getShortCode();
    }

    public String getOriginalUrl(String shortCode) {
        return shortUrlRepository.findByShortCode(shortCode).getOriginalUrl();
    }

    public boolean shortCodeExists(String shortCode){
        return shortUrlRepository.findByShortCode(shortCode) != null;
    }

    public Long getClickCount(String shortCode) {
        return shortUrlRepository.findByShortCode(shortCode).getClickCount();
    }
}
