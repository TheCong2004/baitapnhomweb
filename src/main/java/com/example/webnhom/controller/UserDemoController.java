package com.example.webnhom.controller;

import com.example.webnhom.model.Role;
import com.example.webnhom.model.UserDemo;
import com.example.webnhom.service.CompanyService;
import com.example.webnhom.service.UserDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserDemoController {

    @Autowired
    private UserDemoService userDemoService;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserDemo());
        model.addAttribute("companies", companyService.getAllCompanies());
        return "users/create";
    }



    @PostMapping
    public String createUser(@ModelAttribute UserDemo user, @RequestParam(required = false) Set<Long> roleIds) {
        if (roleIds != null) {
            Set<Role> roles = new HashSet<>();
            for (Long roleId : roleIds) {
                Role role = new Role();
                role.setId(roleId);
                switch (roleId.intValue()) {
                    case 1:
                        role.setName("Nhân viên");
                        break;
                    case 2:
                        role.setName("Trưởng phòng");
                        break;
                    case 3:
                        role.setName("Giám đốc");
                        break;
                }
                roles.add(role);
            }
            user.setRoles(roles);
        }
        userDemoService.saveUser(user);
        return "redirect:/users";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        return userDemoService.getUserById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("companies", companyService.getAllCompanies());
                    System.out.println("User roles: " + user.getRoles()); // Log vai trò
                    return "users/edit";
                })
                .orElse("redirect:/users");
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDemo userDetails, @RequestParam(required = false) Set<Long> roleIds) {
        if (roleIds != null) {
            Set<Role> roles = new HashSet<>();
            for (Long roleId : roleIds) {
                Role role = new Role();
                role.setId(roleId);
                switch (roleId.intValue()) {
                    case 1:
                        role.setName("Nhân viên");
                        break;
                    case 2:
                        role.setName("Trưởng phòng");
                        break;
                    case 3:
                        role.setName("Giám đốc");
                        break;
                }
                roles.add(role);
            }
            userDetails.setRoles(roles);
        }
        userDemoService.updateUser(id, userDetails);
        return "redirect:/users";
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userDemoService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userDemoService.deleteUser(id);
        return "redirect:/users";
    }
}