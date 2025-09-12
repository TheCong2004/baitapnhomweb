package com.example.webnhom.repository;

import com.example.webnhom.model.UserDemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository

public interface UserDemoRepository extends JpaRepository<UserDemo, Long> {
    Optional<UserDemo> findByEmail(String email);
}
