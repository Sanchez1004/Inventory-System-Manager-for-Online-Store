package com.pow.inv_manager.controller;

import com.pow.inv_manager.dto.SupplierDTO;
import com.pow.inv_manager.exception.SupplierException;
import com.pow.inv_manager.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    /**
     * Endpoint to create a new supplier.
     * @param supplierDTO the supplier data to be created
     * @return ResponseEntity containing the created supplier
     */
    @PostMapping("/create")
    public ResponseEntity<SupplierDTO> createSupplier(@RequestBody SupplierDTO supplierDTO) {
        try {
            SupplierDTO createdSupplier = supplierService.createSupplier(supplierDTO);
            return ResponseEntity.ok(createdSupplier);
        } catch (SupplierException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint to update an existing supplier by their ID.
     * @param id the ID of the supplier to update
     * @param supplierDTO the updated supplier data
     * @return ResponseEntity containing the updated supplier
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable Long id, @RequestBody SupplierDTO supplierDTO) {
        try {
            SupplierDTO updatedSupplier = supplierService.updateSupplier(id, supplierDTO);
            return ResponseEntity.ok(updatedSupplier);
        } catch (SupplierException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint to retrieve a supplier by their ID.
     * @param id the ID of the supplier to retrieve
     * @return ResponseEntity containing the supplier information
     */
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long id) {
        try {
            SupplierDTO supplier = supplierService.getSupplierById(id);
            return ResponseEntity.ok(supplier);
        } catch (SupplierException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to retrieve a list of all suppliers.
     * @return ResponseEntity containing the list of all suppliers
     */
    @GetMapping("/all")
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    /**
     * Endpoint to delete a supplier by their ID.
     * @param id the ID of the supplier to delete
     * @return ResponseEntity indicating the result of the delete operation
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        try {
            supplierService.deleteSupplierById(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
