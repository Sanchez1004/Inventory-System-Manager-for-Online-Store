package com.pow.inv_manager.dto.mapper;

import com.pow.inv_manager.dto.InventoryDTO;
import com.pow.inv_manager.model.Inventory;
import org.springframework.stereotype.Service;

@Service
public class InventoryMapper {

    public Inventory toEntity(InventoryDTO inventoryDTO) {
        return Inventory.builder()
                .id(inventoryDTO.getId())
                .isActive(inventoryDTO.isActive())
                .item(inventoryDTO.getItem())
                .location(inventoryDTO.getLocation())
                .price(inventoryDTO.getPrice())
                .quantity(inventoryDTO.getQuantity())
                .build();
    }

    public InventoryDTO toDTO(Inventory inventory) {
        return InventoryDTO.builder()
                .id(inventory.getId())
                .isActive(inventory.isActive())
                .item(inventory.getItem())
                .location(inventory.getLocation())
                .price(inventory.getPrice())
                .quantity(inventory.getQuantity())
                .build();
    }
}

