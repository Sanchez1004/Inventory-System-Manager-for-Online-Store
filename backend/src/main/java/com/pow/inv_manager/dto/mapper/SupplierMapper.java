package com.pow.inv_manager.dto.mapper;

import com.pow.inv_manager.dto.SupplierDTO;
import com.pow.inv_manager.model.Supplier;
import org.springframework.stereotype.Service;

@Service
public class SupplierMapper {
    public SupplierDTO toDTO(Supplier supplier) {
        return SupplierDTO.builder()
                .id(supplier.getId())
                .role(supplier.getRole())
                .email(supplier.getEmail())
                .address(supplier.getAddress())
                .companyName(supplier.getCompanyName())
                .lastName(supplier.getLastName())
                .firstName(supplier.getFirstName())
                .build();
    }

    public Supplier toEntity(SupplierDTO supplierDTO) {
        return Supplier.builder()
                .id(supplierDTO.getId())
                .role(supplierDTO.getRole())
                .email(supplierDTO.getEmail())
                .address(supplierDTO.getAddress())
                .companyName(supplierDTO.getCompanyName())
                .lastName(supplierDTO.getLastName())
                .firstName(supplierDTO.getFirstName())
                .build();
    }
}
