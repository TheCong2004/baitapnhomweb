package com.example.webnhom.controller;

import com.example.webnhom.model.Company;
import com.example.webnhom.model.UserDemo;
import com.example.webnhom.service.CompanyService;
import com.example.webnhom.service.UserDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserDemoService userDemoService;

    // Hiển thị danh sách công ty
    @GetMapping
    public String getAllCompanies(Model model) {
        model.addAttribute("companies", companyService.getAllCompanies());
        return "companies/list";
    }

    // Hiển thị form tạo công ty
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("company", new Company());
        return "companies/create";
    }

    // Xử lý tạo công ty
    @PostMapping
    public String createCompany(@ModelAttribute Company company) {
        companyService.createCompany(company);
        return "redirect:/companies";
    }

    // Hiển thị form sửa công ty
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        return companyService.getCompanyById(id)
                .map(company -> {
                    model.addAttribute("company", company);
                    return "companies/edit";
                })
                .orElse("redirect:/companies");
    }

    // Xử lý sửa công ty
    @PostMapping("/{id}")
    public String updateCompany(@PathVariable Integer id, @ModelAttribute Company companyDetails) {
        companyService.updateCompany(id, companyDetails);
        return "redirect:/companies";
    }

    // Xóa công ty
    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable Integer id) {
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }

    // Hiển thị form thêm người dùng vào công ty
    @GetMapping("/add-user/{id}")
    public String showAddUserForm(@PathVariable Integer id, Model model) {
        return companyService.getCompanyById(id)
                .map(company -> {
                    model.addAttribute("company", company);
                    model.addAttribute("users", userDemoService.getAllUsers());
                    return "companies/add-user";
                })
                .orElse("redirect:/companies");
    }

    // Xử lý thêm người dùng vào công ty
    @PostMapping("/{companyId}/users")
    public String addUserToCompany(@PathVariable Integer companyId, @RequestParam Long userId) {
        companyService.addUserToCompany(companyId, userId);
        return "redirect:/companies";
    }
}