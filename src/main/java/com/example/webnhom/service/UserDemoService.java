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

    // ------------------- AUTH -------------------

    // ✅ Tạo người dùng mới
    public UserDemo saveUser(UserDemo user) {
        return userDemoRepository.save(user);
    }

    // ✅ Kiểm tra email đã tồn tại hay chưa (dùng query trực tiếp)
    public boolean existsByEmail(String email) {
        return userDemoRepository.findByEmailIgnoreCase(email).isPresent();
    }

    // ✅ Xác thực đăng nhập (login)
    public UserDemo authenticate(String email, String password) {
        Optional<UserDemo> userOpt = userDemoRepository.findByEmailIgnoreCase(email);
        if (userOpt.isPresent()) {
            UserDemo user = userOpt.get();
            // TODO: nên mã hóa và so sánh bằng passwordEncoder.matches()
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // ------------------- ADMIN -------------------

    // ✅ Lấy danh sách tất cả người dùng
    public List<UserDemo> getAllUsers() {
        return userDemoRepository.findAll();
    }

    // ✅ Lấy thông tin người dùng theo ID
    public UserDemo getUserById(Long id) {
        return userDemoRepository.findById(id).orElse(null);
    }

    // ✅ Cập nhật thông tin người dùng
    public UserDemo updateUser(UserDemo userDetails) {
        Optional<UserDemo> user = userDemoRepository.findById(userDetails.getId());
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

    // ✅ Xóa người dùng
    public void deleteUser(Long id) {
        userDemoRepository.deleteById(id);
    }
}
