package com.pow.inv_manager.service.implementation;

import com.pow.inv_manager.dto.AdminDTO;
import com.pow.inv_manager.dto.mapper.AdminMapper;
import com.pow.inv_manager.dto.mapper.AddressMapper;
import com.pow.inv_manager.exception.AdminException;
import com.pow.inv_manager.model.Admin;
import com.pow.inv_manager.repository.AdminRepository;
import com.pow.inv_manager.service.AddressService;
import com.pow.inv_manager.service.AdminService;
import com.pow.inv_manager.utils.Role;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private static final String ADMIN_NOT_FOUND_MESSAGE = "Admin not found with ID: ";

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final AddressService addressService;
    private final AddressMapper addressMapper;

    public AdminServiceImpl(AdminRepository adminRepository, AdminMapper adminMapper, AddressService addressService, AddressMapper addressMapper) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    /**
     * Registers a new admin along with an address if provided.
     *
     * @param adminDTO the data transfer object containing the admin's information
     * @return the saved admin information as a DTO
     * @throws AdminException if the data is invalid or the admin already exists
     */
    @SneakyThrows
    @Override
    @Transactional
    public AdminDTO createAdmin(AdminDTO adminDTO) throws AdminException {
        validateAdminData(adminDTO);

        if (adminRepository.existsById(adminDTO.getId())) {
            throw new AdminException("Admin already exists with ID: " + adminDTO.getId());
        }

        Admin adminEntity = adminMapper.toEntity(adminDTO);
        adminEntity.setRole(Role.ADMIN.toString());
        Admin savedAdmin = adminRepository.save(adminEntity);
        return adminMapper.toDTO(savedAdmin);
    }

    /**
     * Updates an existing admin's profile, including address if provided.
     *
     * @param id the ID of the admin to update
     * @param adminDTO the data transfer object containing updated admin information
     * @return the updated admin information as a DTO
     * @throws AdminException if the admin does not exist or data is invalid
     */
    @Override
    @Transactional
    public AdminDTO updateAdmin(Long id, AdminDTO adminDTO) throws AdminException {
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminException(ADMIN_NOT_FOUND_MESSAGE + id));

        validateAdminData(adminDTO);
        updateAdminFields(existingAdmin, adminDTO);

        Admin updatedAdmin = adminRepository.save(existingAdmin);
        return adminMapper.toDTO(updatedAdmin);
    }

    /**
     * Updates specific fields of an existing admin if new values are provided.
     *
     * @param existingAdmin the current admin entity
     * @param adminDTO the data transfer object containing new admin information
     */
    @SneakyThrows
    private void updateAdminFields(Admin existingAdmin, AdminDTO adminDTO) {
        if (adminDTO.getFirstName() != null && !adminDTO.getFirstName().equals(existingAdmin.getFirstName())) {
            existingAdmin.setFirstName(adminDTO.getFirstName());
        }
        if (adminDTO.getLastName() != null && !adminDTO.getLastName().equals(existingAdmin.getLastName())) {
            existingAdmin.setLastName(adminDTO.getLastName());
        }
        if (adminDTO.getEmail() != null && !adminDTO.getEmail().equals(existingAdmin.getEmail())) {
            existingAdmin.setEmail(adminDTO.getEmail());
        }
        if (adminDTO.getPassword() != null && !adminDTO.getPassword().equals(existingAdmin.getPassword())) {
            existingAdmin.setPassword(adminDTO.getPassword());
        }
    }

    /**
     * Retrieves an admin's profile by ID.
     *
     * @param id the unique identifier of the admin
     * @return the admin profile as a DTO
     * @throws AdminException if the admin does not exist
     */
    @Override
    public AdminDTO getAdmin(Long id) throws AdminException {
        return adminRepository.findById(id)
                .map(adminMapper::toDTO)
                .orElseThrow(() -> new AdminException(ADMIN_NOT_FOUND_MESSAGE + id));
    }

    /**
     * Lists all admins with optional pagination.
     *
     * @return a list of all admins as DTOs
     */
    @Override
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(adminMapper::toDTO)
                .toList();
    }

    /**
     * Delete admin by id, and also delete their address if present.
     *
     * @param id the unique identifier of the admin
     */
    @Override
    @SneakyThrows
    @Transactional
    public void deleteAdminById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminException(ADMIN_NOT_FOUND_MESSAGE + id));

        adminRepository.delete(admin);
    }

    /**
     * Validates essential admin data fields.
     *
     * @param adminDTO the admin data transfer object to validate
     * @throws AdminException if any required field is missing or invalid
     */
    private void validateAdminData(AdminDTO adminDTO) throws AdminException {
        if (adminDTO.getFirstName() == null || adminDTO.getFirstName().isEmpty()) {
            throw new AdminException("Admin's first name is required");
        }
        if (adminDTO.getEmail() == null || adminDTO.getEmail().isEmpty()) {
            throw new AdminException("Admin's email is required");
        }
        if (adminDTO.getPassword() == null || adminDTO.getPassword().isEmpty()) {
            throw new AdminException("Admin's password is required");
        }
    }
}
