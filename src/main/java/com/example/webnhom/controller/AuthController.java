package com.example.webnhom.controller;

import com.example.webnhom.model.UserDemo;
import com.example.webnhom.service.UserDemoService;
import com.example.webnhom.service.CompanyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserDemoService userService;
    @Autowired private CompanyService companyService;

    // ===== REGISTER =====
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new UserDemo());
        model.addAttribute("companies", companyService.getAllCompanies());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDemo user,
                           @RequestParam String confirmPassword,
                           Model model, RedirectAttributes ra) {

        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty() ||
            user.getEmail().isEmpty() || user.getPassword().length() < 6 ||
            !user.getPassword().equals(confirmPassword)) {

            model.addAttribute("error", "Thông tin đăng ký không hợp lệ!");
            model.addAttribute("companies", companyService.getAllCompanies());
            return "auth/register";
        }

        if (userService.getAllUsers().stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            model.addAttribute("error", "Email đã tồn tại!");
            model.addAttribute("companies", companyService.getAllCompanies());
            return "auth/register";
        }

        userService.saveUser(user);
        ra.addFlashAttribute("success", "Đăng ký thành công!");
        return "redirect:/auth/login";
    }

    // ===== LOGIN =====
    @GetMapping("/login")
    public String showLogin() { return "auth/login"; }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        HttpSession session, Model model, RedirectAttributes ra) {

        UserDemo user = userService.getAllUsers().stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst().orElse(null);

        if (user == null) {
            model.addAttribute("error", "Sai email hoặc mật khẩu!");
            return "auth/login";
        }

        session.setAttribute("currentUser", user);
        ra.addFlashAttribute("success", "Chào mừng " + user.getUsername());
        return "redirect:/auth/dashboard";
    }

    // ===== DASHBOARD =====
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model, RedirectAttributes ra) {
        UserDemo user = (UserDemo) session.getAttribute("currentUser");
        if (user == null) {
            ra.addFlashAttribute("error", "Vui lòng đăng nhập!");
            return "redirect:/auth/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("company", user.getCompany());
        model.addAttribute("roles", user.getRoles());
        return "auth/dashboard";
    }

    // ===== LOGOUT =====
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes ra) {
        session.invalidate();
        ra.addFlashAttribute("success", "Đăng xuất thành công!");
        return "redirect:/auth/login";
    }
}
