package com.pow.inv_manager.service;

import com.pow.inv_manager.dto.ItemDTO;
import com.pow.inv_manager.exception.ItemException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    @Transactional
    ItemDTO updateItem(Long id, ItemDTO itemDTO) throws ItemException;

    @Transactional
    void deleteItem(Long id) throws ItemException;

    ItemDTO getItemById(Long id) throws ItemException;

    List<ItemDTO> listItems(Optional<String> category, Optional<Long> supplierId,
                            Optional<Double> minPrice, Optional<Double> maxPrice);
}
