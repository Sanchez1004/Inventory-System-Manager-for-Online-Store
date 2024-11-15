package com.pow.inv_manager.dto.mapper;

import com.pow.inv_manager.dto.AdminDTO;
import com.pow.inv_manager.model.Admin;
import org.springframework.stereotype.Service;

@Service
public class AdminMapper {
    public AdminDTO toDTO(Admin admin) {
        return AdminDTO.builder()
                .id(admin.getId())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .password(admin.getPassword())
                .role(admin.getRole())
                .build();
    }

    public Admin toEntity(AdminDTO adminDTO) {
        return Admin.builder()
                .id(adminDTO.getId())
                .firstName(adminDTO.getFirstName())
                .lastName(adminDTO.getLastName())
                .username(adminDTO.getUsername())
                .email(adminDTO.getEmail())
                .password(adminDTO.getPassword())
                .role(adminDTO.getRole())
                .build();
    }
}
