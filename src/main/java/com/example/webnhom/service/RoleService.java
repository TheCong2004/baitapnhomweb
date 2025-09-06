package com.example.webnhom.service;

import com.example.webnhom.model.Role;
import com.example.webnhom.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Lấy danh sách tất cả vai trò
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Lấy vai trò theo ID
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    // Tạo vai trò mới
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    // Cập nhật vai trò
    public Role updateRole(Long id, Role roleDetails) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            Role existingRole = role.get();
            existingRole.setName(roleDetails.getName());
            return roleRepository.save(existingRole);
        }
        return null;
    }

    // Xóa vai trò
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    // Tìm vai trò theo tên
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}