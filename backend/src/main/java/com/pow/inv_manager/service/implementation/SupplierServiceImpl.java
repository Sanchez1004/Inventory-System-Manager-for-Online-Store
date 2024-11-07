package com.pow.inv_manager.service.implementation;

import com.pow.inv_manager.dto.SupplierDTO;
import com.pow.inv_manager.dto.mapper.SupplierMapper;
import com.pow.inv_manager.model.Supplier;
import com.pow.inv_manager.exception.SupplierException;
import com.pow.inv_manager.repository.SupplierRepository;
import com.pow.inv_manager.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    /**
     * Registers a new supplier.
     *
     * @param supplierDTO the data transfer object containing the supplier's information
     * @return the saved supplier information as a DTO
     * @throws SupplierException if the data is invalid or supplier already exists
     */
    @Override
    public SupplierDTO createSupplier(SupplierDTO supplierDTO) throws SupplierException {
        validateSupplierData(supplierDTO);

        if (supplierRepository.existsById(supplierDTO.getId())) {
            throw new SupplierException("Supplier already exists with ID: " + supplierDTO.getId());
        }

        Supplier supplierEntity = supplierMapper.toEntity(supplierDTO);
        Supplier savedSupplier = supplierRepository.save(supplierEntity);

        // TODO: Add role creation logic for supplier user here when implemented Spring Security

        return supplierMapper.toDTO(savedSupplier);
    }

    /**
     * Updates an existing supplier profile.
     *
     * @param id          the ID of the supplier to update
     * @param supplierDTO the data transfer object containing updated supplier information
     * @return the updated supplier information as a DTO
     * @throws SupplierException if the supplier does not exist or data is invalid
     */
    @Override
    public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) throws SupplierException {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierException("Supplier not found with ID: " + id));

        validateSupplierData(supplierDTO);
        updateSupplierFields(existingSupplier, supplierDTO);

        Supplier updatedSupplier = supplierRepository.save(existingSupplier);
        return supplierMapper.toDTO(updatedSupplier);
    }

    /**
     * Updates the fields of an existing supplier only if the new values are different.
     *
     * @param existingSupplier the current supplier entity
     * @param supplierDTO      the data transfer object containing new supplier information
     */
    private void updateSupplierFields(Supplier existingSupplier, SupplierDTO supplierDTO) {
        if (supplierDTO.getId() != null && !supplierDTO.getId().equals(existingSupplier.getId())) {
            existingSupplier.setId(supplierDTO.getId());
        }
        if (supplierDTO.getFirstName() != null && !supplierDTO.getFirstName().equals(existingSupplier.getFirstName())) {
            existingSupplier.setFirstName(supplierDTO.getFirstName());
        }
        if (supplierDTO.getLastName() != null && !supplierDTO.getLastName().equals(existingSupplier.getLastName())) {
            existingSupplier.setLastName(supplierDTO.getLastName());
        }
        if (supplierDTO.getCompanyName() != null && !supplierDTO.getCompanyName().equals(existingSupplier.getCompanyName())) {
            existingSupplier.setCompanyName(supplierDTO.getCompanyName());
        }
        if (supplierDTO.getEmail() != null && !supplierDTO.getEmail().equals(existingSupplier.getEmail())) {
            existingSupplier.setEmail(supplierDTO.getEmail());
        }
        if (supplierDTO.getAddress() != null && !supplierDTO.getAddress().equals(existingSupplier.getAddress())) {
            existingSupplier.setAddress(supplierDTO.getAddress());
        }
    }

    /**
     * Retrieves a supplier's profile by ID.
     *
     * @param id the unique identifier of the supplier
     * @return the supplier profile as a DTO
     * @throws SupplierException if the supplier does not exist
     */
    @Override
    public SupplierDTO getSupplierById(Long id) throws SupplierException {
        return supplierRepository.findById(id)
                .map(supplierMapper::toDTO)
                .orElseThrow(() -> new SupplierException("Supplier not found with ID: " + id));
    }

    /**
     * Lists all suppliers with optional pagination.
     *
     * @return a list of all suppliers as DTOs
     */
    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Validates essential supplier data fields.
     *
     * @param supplierDTO the supplier data transfer object to validate
     * @throws SupplierException if any required field is missing or invalid
     */
    private void validateSupplierData(SupplierDTO supplierDTO) throws SupplierException {
        if (supplierDTO.getFirstName() == null || supplierDTO.getFirstName().isEmpty()) {
            throw new SupplierException("Supplier's first name is required");
        }
        if (supplierDTO.getCompanyName() == null || supplierDTO.getCompanyName().isEmpty()) {
            throw new SupplierException("Supplier's company name is required");
        }
        if (supplierDTO.getEmail() == null || supplierDTO.getEmail().isEmpty()) {
            throw new SupplierException("Supplier's email is required");
        }
        if (supplierDTO.getAddress() == null) {
            throw new SupplierException("Supplier's address is required");
        }
    }
}