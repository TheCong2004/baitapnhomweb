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

    // Tạo công ty mới
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    // Lấy danh sách tất cả công ty
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    // Lấy thông tin công ty theo ID
    public Optional<Company> getCompanyById(Integer id) {
        return companyRepository.findById(id);
    }

    // Cập nhật thông tin công ty
    public Company updateCompany(Integer id, Company companyDetails) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            Company existingCompany = company.get();
            existingCompany.setCompanyName(companyDetails.getCompanyName());
            existingCompany.setUsers(companyDetails.getUsers());
            return companyRepository.save(existingCompany);
        }
        return null;
    }

    // Xóa công ty
    public void deleteCompany(Integer id) {
        companyRepository.deleteById(id);
    }

    // Thêm người dùng vào công ty
    public Company addUserToCompany(Integer companyId, Long userId) {
        Optional<Company> company = companyRepository.findById(companyId);
        Optional<UserDemo> user = userDemoRepository.findById(userId);
        if (company.isPresent() && user.isPresent()) {
            Company existingCompany = company.get();
            UserDemo existingUser = user.get();
            existingUser.setCompany(existingCompany);
            existingCompany.getUsers().add(existingUser);
            userDemoRepository.save(existingUser);
            return companyRepository.save(existingCompany);
        }
        return null;
    }
}
