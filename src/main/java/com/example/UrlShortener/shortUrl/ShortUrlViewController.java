package com.example.UrlShortener.shortUrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
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
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("originalUrl", originalUrl);
        ResponseEntity<Map> response = restTemplate.postForEntity(API_BASE_URL + "/randomUrl", requestBody, Map.class);
        if (response.getStatusCode() == HttpStatusCode.valueOf(409))
            model.addAttribute("shortUrl", "already in use, try another one");
        else
            model.addAttribute("shortUrl", response.getBody().get("shortUrl"));
        return "home";
    }

    @PostMapping("/generate-custom")
    public String generateCustomShortUrl(@RequestParam String originalUrl, @RequestParam String customCode, Model model) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("originalUrl", originalUrl);
        requestBody.put("customCode", customCode);
        ResponseEntity<Map> response = restTemplate.postForEntity(API_BASE_URL + "/customUrl", requestBody, Map.class);
        model.addAttribute("customUrl", response.getBody().get("customUrl"));
        return "home";
    }

    @GetMapping("/go/{shortCode}")
    public String getOriginalUrl(@PathVariable("shortCode") String shortCode) {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                API_BASE_URL + "/originalUrl?shortCode=" + UriUtils.encode(shortCode, StandardCharsets.UTF_8), Map.class);
        return "redirect:" + response.getBody().get("originalUrl");
    }
}
