package com.sep.nc.controller;

import com.sep.nc.entity.User;
import com.sep.nc.entity.dto.UserDto;
import com.sep.nc.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/{register}")
    public ResponseEntity registerUser(@RequestBody UserDto userDto) {
        User user = this.userService.registerUser(userDto);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
