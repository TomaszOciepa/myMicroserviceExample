package com.apptomio.adminservice.service;

import com.apptomio.adminservice.model.Admin;

import java.util.List;

public interface AdminService {

    List<Admin> getAllAdmins();
    Admin getAdminById(String id);
    Admin getAdminByEmail(String email);
    Admin addAdmin(Admin admin);
    Admin putAdmin(String id, Admin admin);
    Admin patchAdmin(String id, Admin admin);
    void deleteAdmin(String id);

}
