package com.pow.inv_manager.controller;

import com.pow.inv_manager.dto.InventoryDTO;
import com.pow.inv_manager.exception.InventoryException;
import com.pow.inv_manager.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Endpoint to add a new item to the inventory.
     * @param inventoryDTO the inventory data to be added
     * @return ResponseEntity containing the added inventory item
     */
    @PostMapping("/add")
    public ResponseEntity<InventoryDTO> addInventory(@RequestBody InventoryDTO inventoryDTO) {
        try {
            InventoryDTO addedInventory = inventoryService.addInventory(inventoryDTO);
            return ResponseEntity.ok(addedInventory);
        } catch (InventoryException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventoryDTO) {
        try {
            InventoryDTO createdInventory = inventoryService.addInventory(inventoryDTO);
            return ResponseEntity.status(201).body(createdInventory);
        } catch (InventoryException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint to update an existing inventory item by its ID.
     * @param id the ID of the inventory item to update
     * @param inventoryDTO the updated inventory data
     * @return ResponseEntity containing the updated inventory item
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO) {
        try {
            InventoryDTO updatedInventory = inventoryService.updateInventory(id, inventoryDTO);
            return ResponseEntity.ok(updatedInventory);
        } catch (InventoryException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint to reduce inventory quantity for an item.
     * @param inventoryDTO the inventory data with reduced quantity
     * @return ResponseEntity indicating the result of the operation
     */
    @PutMapping("/reduce")
    public ResponseEntity<Void> reduceInventory(@RequestBody InventoryDTO inventoryDTO) {
        try {
            inventoryService.reduceInventory(inventoryDTO);
            return ResponseEntity.noContent().build();
        } catch (InventoryException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint to mark an inventory item as inactive.
     * @param id the ID of the inventory item to mark as inactive
     * @return ResponseEntity indicating the result of the operation
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        try {
            inventoryService.deleteInventory(id);
            return ResponseEntity.noContent().build();
        } catch (InventoryException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to retrieve an inventory item by its ID.
     * @param id the ID of the inventory item to retrieve
     * @return ResponseEntity containing the inventory item
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Long id) {
        try {
            InventoryDTO inventory = inventoryService.getInventoryById(id);
            return ResponseEntity.ok(inventory);
        } catch (InventoryException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to retrieve all inventory items.
     * @return ResponseEntity containing the list of all inventory items
     */
    @GetMapping("/all")
    public ResponseEntity<List<InventoryDTO>> getAllInventory() {
        List<InventoryDTO> inventoryList = inventoryService.getInventory();
        return ResponseEntity.ok(inventoryList);
    }
}
