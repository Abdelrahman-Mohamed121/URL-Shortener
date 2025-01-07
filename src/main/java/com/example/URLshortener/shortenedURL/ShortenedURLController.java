package com.example.URLshortener.shortenedURL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortenedURLController {
    @Autowired
    ShortenedURLService shortenedURLService;
}
