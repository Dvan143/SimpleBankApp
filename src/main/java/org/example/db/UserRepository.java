package org.example.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    AppUser findById(long id);
    Boolean existsByUsername(String name);
    String getRoleByUsername(String username);
}
