package com.apptomio.adminservice.service;

import com.apptomio.adminservice.exception.AdminError;
import com.apptomio.adminservice.exception.AdminException;
import com.apptomio.adminservice.model.Admin;
import com.apptomio.adminservice.model.Status;
import com.apptomio.adminservice.repo.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdminById(String id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminException(AdminError.ADMIN_NOT_FOUND));
        return admin;
    }

    @Override
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new AdminException(AdminError.ADMIN_NOT_FOUND));
    }

    @Override
    public Admin addAdmin(Admin admin) {
        validateAdminEmailExists(admin);
        admin.setStatus(Status.ACTIVE);
        return adminRepository.save(admin);
    }

    @Override
    public Admin putAdmin(String id, Admin admin) {
        return adminRepository.findById(id)
                .map(
                        adminFromDb -> {
                            if (!adminFromDb.getEmail().equals(admin.getEmail())
                                    && adminRepository.existsByEmail(admin.getEmail())) {
                                throw new AdminException(AdminError.ADMIN_EMAIL_ALREADY_EXISTS);
                            }
                            adminFromDb.setFirstName(admin.getFirstName());
                            adminFromDb.setLastName(admin.getLastName());
                            adminFromDb.setEmail(admin.getEmail());
                            return adminRepository.save(adminFromDb);
                        }
                )
                .orElseThrow(() -> new AdminException(AdminError.ADMIN_NOT_FOUND));
    }

    @Override
    public Admin patchAdmin(String id, Admin admin) {
        Admin adminFromDb =  adminRepository.findById(id)
                .orElseThrow(()-> new AdminException(AdminError.ADMIN_NOT_FOUND));
        if(admin.getFirstName() != null){
            adminFromDb.setFirstName(admin.getFirstName());
        }
        if(admin.getLastName() != null){
            adminFromDb.setLastName(admin.getLastName());
        }
        if(admin.getEmail() != null){
            adminFromDb.setEmail(admin.getEmail());
        }
        if(admin.getStatus() != null){
            adminFromDb.setStatus(admin.getStatus());
        }

        return adminRepository.save(adminFromDb);
    }

    @Override
    public void deleteAdmin(String id) {
       Admin admin = adminRepository.findById(id)
                .orElseThrow(()-> new AdminException(AdminError.ADMIN_NOT_FOUND));
       admin.setStatus(Status.INACTIVE);
       adminRepository.save(admin);
    }

    private void validateAdminEmailExists(Admin admin) {
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new AdminException(AdminError.ADMIN_EMAIL_ALREADY_EXISTS);
        }
    }
}
