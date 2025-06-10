package org.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class PageController {
    @GetMapping("/main")
    public String main(){
        return "index";
    }
    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
    @GetMapping("/user")
    public String user(){
        return "user";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }
    @GetMapping("/register")
    public String register(){
        return "register";
    }
}
