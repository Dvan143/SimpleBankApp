package org.example.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepository UserRepository;
    @Autowired
    public UserService(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    public List<User> findAll() {
        return UserRepository.findAll();
    }
    public User findById(long id) {
        return UserRepository.findById(id);
    }
    public void deleteById(long id) {
        UserRepository.deleteById(id);
    }
    public void save(User user) {
        UserRepository.save(user);
    }
    public boolean existByName(String name) {
        return UserRepository.existsByUsername(name);
    }
    public boolean existById(Long id) {
        return UserRepository.existsById(id);
    }
    public User findByUsername(String username) {
        return UserRepository.findByUsername(username);
    }
}
