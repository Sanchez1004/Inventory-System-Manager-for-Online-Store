package com.pow.inv_manager.dto.mapper;

import com.pow.inv_manager.dto.InventoryDTO;
import com.pow.inv_manager.model.Inventory;
import org.springframework.stereotype.Service;

@Service
public class InventoryMapper {

    public Inventory toEntity(InventoryDTO inventoryDTO) {
        return Inventory.builder()
                .id(inventoryDTO.getId())
                .isActive(inventoryDTO.getIsActive())
                .item(inventoryDTO.getItem())
                .location(inventoryDTO.getLocation())
                .quantity(inventoryDTO.getQuantity())
                .build();
    }

    public InventoryDTO toDTO(Inventory inventory) {
        return InventoryDTO.builder()
                .id(inventory.getId())
                .isActive(inventory.getIsActive())
                .item(inventory.getItem())
                .location(inventory.getLocation())
                .quantity(inventory.getQuantity())
                .build();
    }
}

