package com.pow.inv_manager.dto.mapper;

import com.pow.inv_manager.dto.SupplierDTO;
import com.pow.inv_manager.model.Supplier;
import org.springframework.stereotype.Service;

@Service
public class SupplierMapper {
    public SupplierDTO toDTO(Supplier supplier) {
        return SupplierDTO.builder()

                .build();
    }

    public Supplier toEntity(SupplierDTO supplierDTO) {
        return Supplier.builder()

                .build();
    }
}
