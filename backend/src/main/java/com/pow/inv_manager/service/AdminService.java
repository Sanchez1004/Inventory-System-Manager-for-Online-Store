package com.pow.inv_manager.service;

import com.pow.inv_manager.dto.AdminDTO;
import com.pow.inv_manager.dto.AuthResponse;
import com.pow.inv_manager.dto.LoginRequest;
import com.pow.inv_manager.exception.AdminException;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdminService {
    @SneakyThrows
    AuthResponse login(LoginRequest loginRequest);

    @SneakyThrows
    AuthResponse register(AdminDTO adminDTO);

    AdminDTO createAdmin(AdminDTO adminDTO) throws AdminException;

    AdminDTO updateAdmin(Long adminId, AdminDTO adminDTO) throws AdminException;

    AdminDTO getAdmin(Long adminId) throws AdminException;

    List<AdminDTO> getAllAdmins();

    @Transactional
    void deleteAdminById(Long id);
}
