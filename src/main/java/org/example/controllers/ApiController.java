package org.example.controllers;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.AppUser;
import org.example.db.UserService;
import org.example.db.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.*;
import java.util.Optional;

@Controller
public class ApiController {
    // Getting access to db
    UserService userService;
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    @Autowired
    public ApiController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // API
    @GetMapping("/users")
    public ResponseEntity index(){
        return new ResponseEntity<>(userService.findAll().stream().map(user -> String.join("-",user.getUsername(), user.getRole())).collect(Collectors.toList()), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity login(HttpServletResponse resp, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            Authentication auth = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(auth);

            Cookie cookie = new Cookie("Token", jwtService.generateToken(username));
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 24 * 3);
            resp.addCookie(cookie);
            resp.sendRedirect("/");
        } catch (AuthenticationException | IOException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("login or password are wrong");
        }
        return null;
    }
    @PostMapping("/register")
    public ResponseEntity newUser(@RequestParam("username") String username, @RequestParam("password") String password){
        if (userService.existByName(username)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else{
            userService.save(new AppUser(username,passwordEncoder.encode(password),"user"));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
    @DeleteMapping("/api/removeUser/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id){
        if (userService.existById(id)){
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/api/profile/{name}")
    public ResponseEntity getProfile(@PathVariable("name") String name){
        if (userService.findByUsername(name).isPresent()){
            return new ResponseEntity<>(userService.findByUsername(name).get(), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/api/getAdmins")
    public ResponseEntity getAdmins(){
        List list = userService.findAll().stream()
                .filter(user -> user.getRole()=="admin")
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
}
