package com.example.webnhom.controller;

import com.example.webnhom.model.UserDemo;
import com.example.webnhom.service.UserDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserDemoService userService;

    // ----------- TRANG CHÍNH ADMIN (redirect sang /users) -----------
    @GetMapping("")
    public String adminHome() {
        return "redirect:/admin/users";
    }

    // ----------- HIỂN THỊ DANH SÁCH NGƯỜI DÙNG -----------
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<UserDemo> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user-list";  // file user-list.html
    }

    // ----------- FORM SỬA NGƯỜI DÙNG -----------
    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        UserDemo user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/admin/users"; // Nếu không tìm thấy thì quay lại danh sách
        }
        model.addAttribute("user", user);
        return "admin/edit-user"; // file edit-user.html
    }

    // ----------- XỬ LÝ CẬP NHẬT NGƯỜI DÙNG -----------
    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute UserDemo user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    // ----------- XÓA NGƯỜI DÙNG -----------
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
