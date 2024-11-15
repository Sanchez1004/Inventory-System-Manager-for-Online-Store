package com.pow.inv_manager.service;

import com.pow.inv_manager.dto.InventoryDTO;
import com.pow.inv_manager.exception.InventoryException;
import com.pow.inv_manager.utils.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    @Transactional
    InventoryDTO addInventory(InventoryDTO inventoryDTO) throws InventoryException;

    @Transactional
    InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) throws InventoryException;

    @Transactional
    void reduceInventory(InventoryDTO inventoryDTO) throws InventoryException;

    @Transactional
    void deleteInventory(Long id) throws InventoryException;

    InventoryDTO getInventoryById(Long id) throws InventoryException;

    List<InventoryDTO> getInventory();
}
