package com.example.webnhom.service;

import com.example.webnhom.model.UserDemo;
import com.example.webnhom.repository.UserDemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDemoService {

    @Autowired
    private UserDemoRepository userDemoRepository;

    // Tạo người dùng mới
    public UserDemo saveUser(UserDemo user) {
        return userDemoRepository.save(user);
    }

    // Lấy danh sách tất cả người dùng
    public List<UserDemo> getAllUsers() {
        return userDemoRepository.findAll();
    }

    // Lấy thông tin người dùng theo ID
    public Optional<UserDemo> getUserById(Long id) {
        return userDemoRepository.findById(id);
    }

    // Cập nhật thông tin người dùng
    public UserDemo updateUser(Long id, UserDemo userDetails) {
        Optional<UserDemo> user = userDemoRepository.findById(id);
        if (user.isPresent()) {
            UserDemo existingUser = user.get();
            existingUser.setFirstName(userDetails.getFirstName());
            existingUser.setLastName(userDetails.getLastName());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setRoles(userDetails.getRoles());
            existingUser.setCompany(userDetails.getCompany());
            return userDemoRepository.save(existingUser);
        }
        return null;
    }

    // Xóa người dùng
    public void deleteUser(Long id) {
        userDemoRepository.deleteById(id);
    }
}