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
    public ResponseEntity<Map<String, String>> generateRandomURL(RedirectAttributes attributes, @RequestParam("originalUrl") String originalUrl) {
        String shortUrl = shortURLService.generateRandomURL(originalUrl);
        return ResponseEntity.ok(Map.of("shortUrl", shortUrl));
    }

    @PostMapping("/customURL")
    public ResponseEntity<Map<String, String>> generateCustomURL(RedirectAttributes attributes,
                                                                 @RequestParam("originalUrl") String originalUrl,
                                                                 @RequestParam("customCode") String customCode) {
        if (shortURLService.shortCodeExists(customCode))
            return new ResponseEntity<>(Map.of("message", "URL already in use, try another one"), HttpStatus.CONFLICT);
        String customUrl = shortURLService.generateCustomURL(originalUrl, customCode);
        return ResponseEntity.ok(Map.of("customUrl", customUrl));
    }

    @GetMapping("/originalURL")
    public ResponseEntity<Map<String, String>> getOriginalUrl(RedirectAttributes attributes, @RequestParam("shortCode") String shortCode) {
        String originalUrl = shortURLService.getOriginalURL(shortCode);
        return ResponseEntity.ok(Map.of("originalURL", originalUrl));
    }

    @GetMapping("/clickCount")
    public ResponseEntity<Map<String, String>> getClickCount(RedirectAttributes attributes, @RequestParam("shortCode") String shortCode) {
        String clickCount = shortURLService.getClickCount(shortCode).toString();
        return ResponseEntity.ok(Map.of("clickCount", clickCount));
    }

}
