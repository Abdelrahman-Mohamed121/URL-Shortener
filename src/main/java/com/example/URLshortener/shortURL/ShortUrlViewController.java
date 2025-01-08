package com.example.URLshortener.shortURL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ShortUrlViewController {

    private final RestTemplate restTemplate;

    @Autowired
    public ShortUrlViewController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String API_BASE_URL = "http://localhost:8080/api/v1/";

    @GetMapping
    public String homePage() {
        return "home";
    }

    @PostMapping("/generate-random")
    public String generateRandomShortUrl(@RequestParam("originalUrl") String originalUrl, Model model) {
        // Prepare request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("originalUrl", originalUrl);
        // Call the API to generate the random short URL
        ResponseEntity<Map> response = restTemplate.postForEntity(API_BASE_URL + "/randomURL", requestBody, Map.class);

        // Add the response data to the model for rendering
        model.addAttribute("shortUrl", response.getBody().get("shortUrl"));
        return "home";
    }

    @PostMapping("/generate-custom")
    public String generateCustomShortUrl(@RequestParam String originalUrl, @RequestParam String customCode, Model model) {
        // Prepare request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("originalUrl", originalUrl);
        requestBody.put("customCode", customCode);

        // Call the API to generate the custom short URL
        ResponseEntity<Map> response = restTemplate.postForEntity(API_BASE_URL + "/custom", requestBody, Map.class);

        // Add the response data to the model for rendering
        model.addAttribute("customURL", response.getBody().get("customURL"));
        return "home";
    }
}
