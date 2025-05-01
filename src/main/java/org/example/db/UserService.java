package org.example.db;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
    public User findById(long id) {
        return userRepository.findById(id);
    }
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
    public boolean existByName(String name) {
        return userRepository.existsByUsername(name);
    }
    public boolean existById(Long id) {
        return userRepository.existsById(id);
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public String getRoleByUsername(String username) {
        return userRepository.getRoleByUsername(username);
    }
    //    public void save(User user) {
    //        userRepository.save(user);
    //    }
    public void save(User user){
        User newUser = new User();
        if (user.getRole()==null){
            newUser = new User(user.getUsername(),user.getPassword(),"user");
        } else{
            newUser = new User(user.getUsername(),user.getPassword(),user.getRole());
        }
        userRepository.save(newUser);
    }

    // Creating admin user
    @PostConstruct
    public void init() {
        userRepository.save(new User("admin","admin","admin"));
    }
}
