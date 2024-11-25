package com.pow.inv_manager.service.implementation;

import com.pow.inv_manager.dto.ItemDTO;
import com.pow.inv_manager.dto.mapper.ItemMapper;
import com.pow.inv_manager.exception.ItemException;
import com.pow.inv_manager.model.Inventory;
import com.pow.inv_manager.model.Item;
import com.pow.inv_manager.model.OrderItem;
import com.pow.inv_manager.repository.InventoryRepository;
import com.pow.inv_manager.repository.ItemRepository;
import com.pow.inv_manager.repository.OrderItemRepository;
import com.pow.inv_manager.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private static final String ITEM_NOT_FOUND_MESSAGE = "Item not found with ID: ";

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final InventoryRepository inventoryRepository;
    private final OrderItemRepository orderItemRepository;

    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper, InventoryRepository inventoryRepository, OrderItemRepository orderItemRepository) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.inventoryRepository = inventoryRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    @Override
    public ItemDTO createItem(ItemDTO itemDTO) throws ItemException {
        validateItemData(itemDTO);

        Item item = itemMapper.toEntity(itemDTO);
        Item savedItem = itemRepository.save(item);

        Inventory inventory = Inventory.builder()
                .item(savedItem)
                .quantity(0)
                .location("")
                .isActive(false)
                .build();
        inventoryRepository.save(inventory);
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

        Optional<Inventory> existingInventory = inventoryRepository.findById(id);
        OrderItem orderItem = orderItemRepository.findByInventory_Id(id);

        if (orderItem != null) {
            orderItemRepository.delete(orderItem);
        }

        existingInventory.ifPresent(inventoryRepository::delete);

        itemRepository.delete(existingItem);
    }

    @Override
    public ItemDTO getItemById(Long id) throws ItemException {
        return itemRepository.findById(id)
                .map(itemMapper::toDTO)
                .orElseThrow(() -> new ItemException(ITEM_NOT_FOUND_MESSAGE + id));
    }

    @Override
    public List<ItemDTO> listItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(itemMapper::toDTO)
                .toList();
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
        if (itemDTO.getCategory() != null && !itemDTO.getCategory().equals(existingItem.getCategory())) {
            existingItem.setCategory(itemDTO.getCategory());
        }
        if (itemDTO.getPrice() > 0 && itemDTO.getPrice() == existingItem.getPrice()) {
            existingItem.setPrice(itemDTO.getPrice());
        }
        if (itemDTO.getPhoto() != null && !itemDTO.getPhoto().equals(existingItem.getPhoto())) {
            existingItem.setPhoto(itemDTO.getPhoto());
        }
    }
}
