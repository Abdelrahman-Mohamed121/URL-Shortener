package com.example.URLshortener.shortenedURL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api")
public class ShortenedURLRestAPIController {
    @Autowired
    ShortenedURLService shortenedURLService;

    @PostMapping("/randomURL")
    public String generateRandomURL(RedirectAttributes attributes, @RequestParam("randomOriginalUrl") String randomOriginalUrl){
        return shortenedURLService.generateRandomURL(randomOriginalUrl);
    }

}
