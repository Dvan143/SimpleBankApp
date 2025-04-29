package org.example.controllers;


import org.example.db.User;
import org.example.db.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    UserService userService;
    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    public ResponseEntity index(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
    @PostMapping("/api/newUser")
    public ResponseEntity newUser(@RequestBody User user){
        if (userService.existByName(user.getUsername())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else {
            userService.save(new User(user.getUsername(), user.getPassword()));
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
        if (userService.existByName(name)){
            return new ResponseEntity<>(userService.findByUsername(name), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
