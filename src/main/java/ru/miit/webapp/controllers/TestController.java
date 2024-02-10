package ru.miit.webapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Тест
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<HttpStatus> test() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

