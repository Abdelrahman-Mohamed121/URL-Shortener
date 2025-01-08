package com.example.URLshortener.shortURL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ShortUrlApiController {
    @Autowired
    ShortURLService shortURLService;

    @PostMapping("/randomURL")
    public ResponseEntity<Map<String, String>> generateRandomURL(@RequestBody Map<String, String> requestBody) {
        String shortUrl = shortURLService.generateRandomURL(requestBody.get("originalUrl"));
        return ResponseEntity.ok(Map.of("shortUrl", shortUrl));
    }

    @PostMapping("/customURL")
    public ResponseEntity<Map<String, String>> generateCustomURL(@RequestBody Map<String, String> requestBody) {
        if (shortURLService.shortCodeExists(requestBody.get("customCode")))
            return new ResponseEntity<>(Map.of("message", "URL already in use, try another one"), HttpStatus.CONFLICT);
        String customUrl = shortURLService.generateCustomURL(requestBody.get("originalUrl"), requestBody.get("customCode"));
        return ResponseEntity.ok(Map.of("customUrl", customUrl));
    }

    @GetMapping("/originalURL")
    public ResponseEntity<Map<String, String>> getOriginalUrl(@RequestBody Map<String, String> requestBody) {
        String originalUrl = shortURLService.getOriginalURL(requestBody.get("shortCode"));
        return ResponseEntity.ok(Map.of("originalURL", originalUrl));
    }

    @GetMapping("/clickCount")
    public ResponseEntity<Map<String, String>> getClickCount(@RequestBody Map<String, String> requestBody) {
        String clickCount = shortURLService.getClickCount(requestBody.get("shortCode")).toString();
        return ResponseEntity.ok(Map.of("clickCount", clickCount));
    }

}
