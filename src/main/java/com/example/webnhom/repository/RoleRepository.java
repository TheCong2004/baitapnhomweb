package com.example.webnhom.repository;

import com.example.webnhom.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Tìm Role theo tên (nếu cần)
    Role findByName(String name);
}