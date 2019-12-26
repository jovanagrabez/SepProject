package com.sep.nc.controller;

import com.sep.nc.entity.User;
import com.sep.nc.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(this.userService.getUserByEmail(email), HttpStatus.OK);
    }
}
