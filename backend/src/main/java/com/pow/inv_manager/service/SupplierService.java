package com.pow.inv_manager.service;

import com.pow.inv_manager.dto.SupplierDTO;
import com.pow.inv_manager.exception.SupplierException;

import java.util.List;

public interface SupplierService {
    SupplierDTO createSupplier(SupplierDTO supplierDTO) throws SupplierException;

    SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) throws SupplierException;

    SupplierDTO getSupplierById(Long id) throws SupplierException;

    List<SupplierDTO> getAllSuppliers();
}
