package ru.aberezhnoy.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/v1")
@RestController
public class LoginResource {

    @GetMapping("/login")
    public User login(Authentication auth) {
        return (User) auth.getPrincipal();
    }

    @GetMapping("/logout")
    public void logout(Authentication auth) {
        auth.setAuthenticated(false);
    }
}
