package com.pow.inv_manager.dto.mapper;

import com.pow.inv_manager.dto.ItemDTO;
import com.pow.inv_manager.model.Item;
import org.springframework.stereotype.Service;

@Service
public class ItemMapper {
    public Item toEntity(ItemDTO itemDTO) {
        return Item.builder()
                .id(itemDTO.getId())
                .name(itemDTO.getName())
                .description(itemDTO.getDescription())
                .category(itemDTO.getCategory())
                .price(itemDTO.getPrice())
                .photo(itemDTO.getPhoto())
                .build();
    }

    public ItemDTO toDTO(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .description(item.getDescription())
                .name(item.getName())
                .category(item.getCategory())
                .price(item.getPrice())
                .photo(item.getPhoto())
                .build();
    }
}
