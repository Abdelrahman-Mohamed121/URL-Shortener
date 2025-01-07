package com.example.URLshortener.shortenedURL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ShortenedURLController {
    @Autowired
    ShortenedURLService shortenedURLService;
    @GetMapping("/go/{shortCode}")
    public String getOriginalURL(@PathVariable("shortCode") String shortCode){
        System.out.println(shortCode);
        System.out.println("llink : " + shortenedURLService.getOriginalURL(shortCode));
        return "redirect:" + shortenedURLService.getOriginalURL(shortCode);
    }
}
