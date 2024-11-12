package com.pow.inv_manager.service;

import com.pow.inv_manager.dto.InventoryDTO;
import com.pow.inv_manager.exception.InventoryException;
import java.util.List;

public interface InventoryService {
    InventoryDTO createInventory(InventoryDTO inventoryDTO) throws InventoryException;

    InventoryDTO updateInventory(Long inventoryId, InventoryDTO inventoryDTO) throws InventoryException;

    InventoryDTO getInventory(Long inventoryId) throws InventoryException;

    List<InventoryDTO> getAllInventories() throws InventoryException;
}
