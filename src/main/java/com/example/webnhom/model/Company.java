package com.example.webnhom.model;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "COMPANY")
@Entity
public class Company {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;  // ✅ SỬA: đổi int thành Integer
    
    @Column
    private String companyName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private List<UserDemo> users;

    public List<UserDemo> getUsers() {
        return users;
    }

    public void setUsers(List<UserDemo> users) {
        this.users = users;
    }

    // ✅ SỬA: đổi return type thành Integer
    public Integer getId() {
        return id;
    }

    // ✅ SỬA: đổi parameter type thành Integer
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}