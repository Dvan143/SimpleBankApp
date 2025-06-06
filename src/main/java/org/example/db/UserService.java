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

    public List<AppUser> findAll() {
        return userRepository.findAll();
    }
    public AppUser findById(long id) {
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
    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public String getRoleByUsername(String username) {
        return userRepository.getRoleByUsername(username);
    }
    //    public void save(User user) {
    //        userRepository.save(user);
    //    }
    public void save(AppUser user){
        AppUser newUser = new AppUser();
        if (user.getRole()==null){
            newUser = new AppUser(user.getUsername(),user.getPassword(),"user");
        } else{
            newUser = new AppUser(user.getUsername(),user.getPassword(),user.getRole());
        }
        userRepository.save(newUser);
    }
}
