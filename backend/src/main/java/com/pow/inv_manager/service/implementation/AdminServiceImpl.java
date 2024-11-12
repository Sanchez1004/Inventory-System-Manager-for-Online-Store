package com.pow.inv_manager.service.implementation;

import com.pow.inv_manager.dto.AdminDTO;
import com.pow.inv_manager.dto.mapper.AdminMapper;
import com.pow.inv_manager.model.Admin;
import com.pow.inv_manager.exception.AdminException;
import com.pow.inv_manager.repository.AdminRepository;
import com.pow.inv_manager.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, AdminMapper adminMapper) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
    }

    @Override
    public AdminDTO createAdmin(AdminDTO adminDTO) throws AdminException {
        validateAdminData(adminDTO);

        if (adminRepository.existsById(adminDTO.getId())) {
            throw new AdminException("Admin already exists with ID: " + adminDTO.getId());
        }

        Admin adminEntity = adminMapper.toEntity(adminDTO);
        Admin savedAdmin = adminRepository.save(adminEntity);
        return adminMapper.toDTO(savedAdmin);
    }

    @Override
    public AdminDTO updateAdmin(Long id, AdminDTO adminDTO) throws AdminException {
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminException("Admin not found with ID: " + id));

        updateAdminFields(existingAdmin, adminDTO);
        Admin updatedAdmin = adminRepository.save(existingAdmin);
        return adminMapper.toDTO(updatedAdmin);
    }

    private void updateAdminFields(Admin existingAdmin, AdminDTO adminDTO) {
        if (adminDTO.getName() != null && !adminDTO.getName().equals(existingAdmin.getName())) {
            existingAdmin.setName(adminDTO.getName());
        }
        // Additional fields here
    }

    @Override
    public AdminDTO getAdmin(Long id) throws AdminException {
        return adminRepository.findById(id)
                .map(adminMapper::toDTO)
                .orElseThrow(() -> new AdminException("Admin not found with ID: " + id));
    }

    @Override
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(adminMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void validateAdminData(AdminDTO adminDTO) throws AdminException {
        if (adminDTO.getName() == null || adminDTO.getName().isEmpty()) {
            throw new AdminException("Admin's name is required");
        }
    }
}
//TODO revisar