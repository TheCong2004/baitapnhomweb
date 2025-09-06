package com.example.webnhom.service;

import com.example.webnhom.model.Company;
import com.example.webnhom.model.UserDemo;
import com.example.webnhom.repository.CompanyRepository;
import com.example.webnhom.repository.UserDemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserDemoRepository userDemoRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Optional<Company> getCompanyById(Integer id) {
        return companyRepository.findById(id);
    }

    public Company updateCompany(Integer id, Company companyDetails) {
        return companyRepository.findById(id)
                .map(company -> {
                    company.setCompanyName(companyDetails.getCompanyName());
                    return companyRepository.save(company);
                })
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
    }

    public void deleteCompany(Integer id) {
        companyRepository.deleteById(id);
    }

    // ✅ QUAN TRỌNG: Method để thêm user vào company
    public void addUserToCompany(Integer companyId, Long userId) {
        // Tìm company
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + companyId));

        // Tìm user
        UserDemo user = userDemoRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Set company cho user
        user.setCompany(company);

        // Lưu user (sẽ update foreign key company_id)
        userDemoRepository.save(user);
    }
}