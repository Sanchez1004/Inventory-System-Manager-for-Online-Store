package com.pow.inv_manager.service;

import com.pow.inv_manager.dto.AdminDTO;
import com.pow.inv_manager.exception.AdminException;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdminService {
    AdminDTO createAdmin(AdminDTO adminDTO) throws AdminException;

    AdminDTO updateAdmin(Long adminId, AdminDTO adminDTO) throws AdminException;

    AdminDTO getAdmin(Long adminId) throws AdminException;

    List<AdminDTO> getAllAdmins() throws AdminException;

    @SneakyThrows
    @Transactional
    void deleteAdminById(Long id);
}
