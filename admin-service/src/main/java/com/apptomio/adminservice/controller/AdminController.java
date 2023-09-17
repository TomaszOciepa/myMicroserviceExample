package com.apptomio.adminservice.controller;

import com.apptomio.adminservice.model.Admin;
import com.apptomio.adminservice.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;


    @GetMapping
    public List<Admin> getAllAdmins(){
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable String id){
        return adminService.getAdminById(id);
    }

    @GetMapping("/email")
    public Admin getAdminByEmail(@RequestParam String email){
        return adminService.getAdminByEmail(email);
    }

    @PostMapping
    public Admin addAdmin(@RequestBody Admin admin){
        return adminService.addAdmin(admin);
    }

    @PutMapping("/{id}")
    public Admin putAdmin(@PathVariable String id, @RequestBody Admin admin){
        return adminService.putAdmin(id, admin);
    }

    @PatchMapping("/{id}")
    public Admin patchAdmin(@PathVariable String id, @RequestBody Admin admin){
        return adminService.patchAdmin(id, admin);
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable String id){
        adminService.deleteAdmin(id);
    }
}
