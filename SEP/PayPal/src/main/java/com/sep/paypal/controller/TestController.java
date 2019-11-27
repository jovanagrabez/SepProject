package com.sep.paypal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/test")
public class TestController {

    @PostMapping
    public ResponseEntity testMethod(@RequestBody String message) {
        System.out.println(message);
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
