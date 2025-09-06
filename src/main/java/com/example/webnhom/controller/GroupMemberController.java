package com.example.webnhom.controller;

import com.example.webnhom.model.GroupMember;
import com.example.webnhom.repository.GroupMemberRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*") // cho phép frontend truy cập
public class GroupMemberController {

    private final GroupMemberRepository repo;

    // Constructor injection
    public GroupMemberController(GroupMemberRepository repo) {
        this.repo = repo;
    }

    // Lấy tất cả thành viên
    @GetMapping
    public List<GroupMember> getAll() {
        return repo.findAll();
    }

    // Thêm thành viên mới
    @PostMapping
    public GroupMember create(@RequestBody GroupMember member) {
        return repo.save(member);
    }

    // Xóa thành viên theo id
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    // Cập nhật thành viên
    @PutMapping("/{id}")
    public GroupMember update(@PathVariable Long id, @RequestBody GroupMember updatedMember) {
        return repo.findById(id).map(member -> {
            member.setName(updatedMember.getName());
            member.setRole(updatedMember.getRole());
            return repo.save(member);
        }).orElseThrow(() -> new RuntimeException("Không tìm thấy thành viên với id = " + id));
    }

}
