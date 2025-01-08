package com.example.UrlShortener.shortUrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ShortUrlApiController {
    @Autowired
    ShortUrlService shortUrlService;

    @PostMapping("/randomUrl")
    public ResponseEntity<Map<String, String>> generateRandomUrl(@RequestBody Map<String, String> requestBody) {
        String shortUrl = shortUrlService.generateRandomUrl(requestBody.get("originalUrl"));
        return ResponseEntity.ok(Map.of("shortUrl", shortUrl));
    }

    @PostMapping("/customUrl")
    public ResponseEntity<Map<String, String>> generateCustomUrl(@RequestBody Map<String, String> requestBody) {
        if (shortUrlService.shortCodeExists(requestBody.get("customCode")))
            return new ResponseEntity<>(Map.of("message", "URL already in use, try another one"), HttpStatus.CONFLICT);
        String customUrl = shortUrlService.generateCustomUrl(requestBody.get("originalUrl"), requestBody.get("customCode"));
        return ResponseEntity.ok(Map.of("customUrl", customUrl));
    }

    @GetMapping("/originalUrl")
    public ResponseEntity<Map<String, String>> getOriginalUrl(RedirectAttributes attributes, @RequestParam("shortCode") String shortCode) {
        String originalUrl = shortUrlService.getOriginalUrl(shortCode);
        return ResponseEntity.ok(Map.of("originalUrl", originalUrl));
    }
    @GetMapping("/clickCount")
    public ResponseEntity<Map<String, String>> getClickCount(RedirectAttributes attributes, @RequestParam("shortCode") String shortCode) {
        String clickCount = shortUrlService.getClickCount(shortCode).toString();
        return ResponseEntity.ok(Map.of("clickCount", clickCount));
    }

}
