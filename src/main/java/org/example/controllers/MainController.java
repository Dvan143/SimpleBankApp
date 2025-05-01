package org.example.controllers;


import jakarta.annotation.PostConstruct;
import org.example.db.AppUser;
import org.example.db.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.*;
import java.util.Optional;

@Controller
public class MainController {
    // Getting access to db
    UserService userService;
    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }
    // Main Mapping
    @GetMapping("/main")
    public String main(Model model) {
        return "index";
    }

    // API
    @GetMapping("/users")
    public ResponseEntity index(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
    @PostMapping("/api/newUser")
    public ResponseEntity newUser(@RequestBody AppUser user){
        if (userService.findByUsername(user.getUsername()).isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else{
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
    //    @DeleteMapping("/auth/removeUser")
//    public ResponseEntity deleteUser(@RequestParam("id") Long id){
//        if (userService.existById(id)){
//            userService.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
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
    // Test sec
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
}
