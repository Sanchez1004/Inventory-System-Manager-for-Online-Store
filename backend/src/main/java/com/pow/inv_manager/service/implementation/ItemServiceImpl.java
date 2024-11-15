package com.pow.inv_manager.service.implementation;

import com.pow.inv_manager.dto.ItemDTO;
import com.pow.inv_manager.dto.mapper.ItemMapper;
import com.pow.inv_manager.exception.ItemException;
import com.pow.inv_manager.model.Item;
import com.pow.inv_manager.repository.ItemRepository;
import com.pow.inv_manager.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private static final String ITEM_NOT_FOUND_MESSAGE = "Item not found with ID: ";

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Transactional
    public ItemDTO createItem(ItemDTO itemDTO) throws ItemException {
        validateItemData(itemDTO);

        Item item = itemMapper.toEntity(itemDTO);
        Item savedItem = itemRepository.save(item);
        return itemMapper.toDTO(savedItem);
    }

    @Override
    @Transactional
    public ItemDTO updateItem(Long id, ItemDTO itemDTO) throws ItemException {
        validateItemData(itemDTO);

        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new ItemException(ITEM_NOT_FOUND_MESSAGE + id));

        updateItemFields(existingItem, itemDTO);
        Item updatedItem = itemRepository.save(existingItem);
        return itemMapper.toDTO(updatedItem);
    }

    @Override
    @Transactional
    public void deleteItem(Long id) throws ItemException {
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new ItemException(ITEM_NOT_FOUND_MESSAGE + id));

        itemRepository.save(existingItem);
    }

    @Override
    public ItemDTO getItemById(Long id) throws ItemException {
        return itemRepository.findById(id)
                .map(itemMapper::toDTO)
                .orElseThrow(() -> new ItemException(ITEM_NOT_FOUND_MESSAGE + id));
    }

    @Override
    public List<ItemDTO> listItems(Optional<String> category, Optional<Long> supplierId,
                                   Optional<Double> minPrice, Optional<Double> maxPrice) {
        List<Item> items = itemRepository.findFiltered(category, supplierId, minPrice, maxPrice);
        return items.stream()
                .map(itemMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void validateItemData(ItemDTO itemDTO) throws ItemException {
        if (itemDTO.getName() == null || itemDTO.getName().isEmpty()) {
            throw new ItemException("Item name is required.");
        }
    }

    private void updateItemFields(Item existingItem, ItemDTO itemDTO) {
        if (itemDTO.getName() != null && !itemDTO.getName().equals(existingItem.getName())) {
            existingItem.setName(itemDTO.getName());
        }
        if (itemDTO.getDescription() != null && !itemDTO.getDescription().equals(existingItem.getDescription())) {
            existingItem.setDescription(itemDTO.getDescription());
        }
    }
}
