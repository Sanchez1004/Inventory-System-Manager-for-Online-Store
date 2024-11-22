package com.pow.inv_manager.dto;

import com.pow.inv_manager.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Long id;
    private Item item;
    private int quantity;
    private String location;
    private Boolean isActive;
}
