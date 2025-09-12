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

    // ----------- HIỂN THỊ FORM ĐĂNG KÝ -----------
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new UserDemo());
        model.addAttribute("companies", companyService.getAllCompanies());
        return "auth/register";
    }

    // ----------- XỬ LÝ ĐĂNG KÝ -----------
    @PostMapping("/register")
    public String register(@ModelAttribute UserDemo user,
                           @RequestParam String confirmPassword,
                           Model model, RedirectAttributes ra) {

        // 1. Kiểm tra dữ liệu nhập hợp lệ
        if (user.getFirstName().isEmpty() || user.getLastName().isEmpty() ||
            user.getEmail().isEmpty() || user.getPassword().length() < 6 ||
            !user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Thông tin đăng ký không hợp lệ!");
            model.addAttribute("companies", companyService.getAllCompanies());
            return "auth/register";
        }

        // 2. Kiểm tra email đã tồn tại chưa
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email đã tồn tại!");
            model.addAttribute("companies", companyService.getAllCompanies());
            return "auth/register";
        }

        // 3. Lưu người dùng mới
        userService.saveUser(user); // Có thể mã hóa mật khẩu ở tầng service
        ra.addFlashAttribute("success", "Đăng ký thành công!");
        return "redirect:/auth/login";
    }

    // ----------- HIỂN THỊ FORM ĐĂNG NHẬP -----------
    @GetMapping("/login")
    public String showLogin() {
        return "auth/login";
    }

    // ----------- XỬ LÝ ĐĂNG NHẬP -----------
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        HttpSession session, Model model, RedirectAttributes ra) {

        // 1. Xác thực người dùng
        UserDemo user = userService.authenticate(email, password);

        // 2. Nếu không đúng email hoặc password
        if (user == null) {
            model.addAttribute("error", "Sai email hoặc mật khẩu!");
            return "auth/login";
        }

        // 3. Đúng thì lưu thông tin user vào session và chuyển đến dashboard
        session.setAttribute("currentUser", user);
        ra.addFlashAttribute("success", "Chào mừng " + user.getUsername());
        return "redirect:/auth/dashboard";
    }

    // ----------- DASHBOARD SAU ĐĂNG NHẬP -----------
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

    // ----------- ĐĂNG XUẤT -----------
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes ra) {
        session.invalidate();
        ra.addFlashAttribute("success", "Đăng xuất thành công!");
        return "redirect:/auth/login";
    }
}
