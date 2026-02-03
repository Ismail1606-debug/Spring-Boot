package com.example.demo.controller;


import com.example.demo.Utility.Jwt;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserRequest;
import com.example.demo.model.User;
import com.example.demo.service.DemoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = demoService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = demoService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserById(
            @PathVariable Long id,
            @RequestBody User userRequest
    ) {
        User user = demoService.updateUserById(id, userRequest);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        demoService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/greet")
    public String greeting() {
        return "Hello World";
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UserRequest user) {
        demoService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        User user = demoService.login(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/test/login")
    public ResponseEntity<String> login(HttpServletResponse response){
        String token = Jwt.generateToken(1,"user@gmail.com");
        Cookie cookie = new Cookie("jwt" , token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*1000);
        response.addCookie(cookie);
        return ResponseEntity.ok("Login Successful");
    }

}
