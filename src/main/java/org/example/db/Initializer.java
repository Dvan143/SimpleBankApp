package org.example.db;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Initializer {
    UserService userService;
    @Autowired
    public Initializer(UserService userService) {
        this.userService = userService;
    }
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @PostConstruct
    public void init() {
        AppUser admin = new AppUser("admin", passwordEncoder.encode( "admin"),"admin");
        userService.save(admin);
    }
}
