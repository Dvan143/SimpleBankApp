//package org.example.controllers;
//
//import org.example.db.User;
//import org.example.db.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@RequestMapping("/auth")
//public class Auth {
//    UserService userService;
//    @Autowired
//    public Auth(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/getUsers")
//    public ResponseEntity<List<User>> getUsers() {
//        List<User> users = new ArrayList<>();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity addUser(@RequestBody User user) {
//        try{
//            return userService.createUser(user) ? new ResponseEntity<>(null, HttpStatus.CREATED) : new ResponseEntity<>(null,HttpStatus.IM_USED);
//        }catch(Exception e){
//            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @DeleteMapping
//    public ResponseEntity deleteUserById(@RequestBody User user) {
//        userService.deleteUser(user.getId());
//        return new ResponseEntity<>(null, HttpStatus.OK);
//    }
//}
