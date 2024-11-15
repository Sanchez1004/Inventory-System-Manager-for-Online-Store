package com.pow.inv_manager.service.implementation;

import com.pow.inv_manager.dto.InventoryDTO;
import com.pow.inv_manager.dto.mapper.InventoryMapper;
import com.pow.inv_manager.exception.InventoryException;
import com.pow.inv_manager.model.Inventory;
import com.pow.inv_manager.repository.InventoryRepository;
import com.pow.inv_manager.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing inventory operations, including adding, updating,
 * marking items as inactive, and retrieving inventory items with optional filters.
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    private static final String INVENTORY_NOT_FOUND_MESSAGE = "Inventory item not found with ID: ";

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    /**
     * Constructs an InventoryServiceImpl instance with the required dependencies.
     *
     * @param inventoryRepository the repository for accessing inventory data
     * @param inventoryMapper     the mapper for converting between Inventory and InventoryDTO
     */
    public InventoryServiceImpl(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
    }

    /**
     * Adds a new item to the inventory after validating its data.
     *
     * @param inventoryDTO the DTO containing inventory item data to add
     * @return the added inventory item as a DTO
     * @throws InventoryException if the data is invalid or the item does not exist
     */
    @Override
    @Transactional
    public InventoryDTO addInventory(InventoryDTO inventoryDTO) throws InventoryException {
        if (!inventoryDTO.isActive()) {
            throw new InventoryException("Item is inactive");
        }

        validateInventoryData(inventoryDTO);
        Inventory inventory = inventoryMapper.toEntity(inventoryDTO);
        Inventory savedInventory = inventoryRepository.save(inventory);
        return inventoryMapper.toDTO(savedInventory);
    }

    /**
     * Updates an existing inventory item with new data.
     *
     * @param id           the ID of the inventory item to update
     * @param inventoryDTO the DTO containing the updated inventory data
     * @return the updated inventory item as a DTO
     * @throws InventoryException if the inventory item is not found or data is invalid
     */
    @Override
    @Transactional
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) throws InventoryException {
        validateInventoryData(inventoryDTO);

        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryException(INVENTORY_NOT_FOUND_MESSAGE + id));

        updateInventoryFields(existingInventory, inventoryDTO);
        Inventory updatedInventory = inventoryRepository.save(existingInventory);
        return inventoryMapper.toDTO(updatedInventory);
    }

    @Override
    @Transactional
    public void reduceInventory(InventoryDTO inventoryDTO) throws InventoryException {
        if (!inventoryDTO.isActive()) {
            throw new InventoryException("Item it's not active with id: " + inventoryDTO.getId());
        }
        inventoryRepository.save(inventoryMapper.toEntity(inventoryDTO));

    }

    /**
     * Marks an inventory item as inactive instead of physically deleting it.
     *
     * @param id the ID of the inventory item to mark as inactive
     * @throws InventoryException if the inventory item does not exist
     */
    @Override
    @Transactional
    public void deleteInventory(Long id) throws InventoryException {
        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryException(INVENTORY_NOT_FOUND_MESSAGE + id));

        existingInventory.setActive(false);  // Set to inactive
        inventoryRepository.save(existingInventory);
    }

    /**
     * Retrieves an inventory item by its ID.
     *
     * @param id the ID of the inventory item to retrieve
     * @return the inventory item as a DTO
     * @throws InventoryException if the inventory item is not found
     */
    @Override
    public InventoryDTO getInventoryById(Long id) throws InventoryException {
        return inventoryRepository.findById(id)
                .map(inventoryMapper::toDTO)
                .orElseThrow(() -> new InventoryException(INVENTORY_NOT_FOUND_MESSAGE + id));
    }

    /**
     * Lists all inventory items with optional filtering by category, supplier ID, and price range.
     * @return a list of inventory items as DTOs
     */
    @Override
    public List<InventoryDTO> getInventory() {
        List<Inventory> inventories = inventoryRepository.findAll();

        return inventories.stream()
                .map(inventoryMapper::toDTO)
                .toList();
    }

    /**
     * Validates essential fields of the inventory item data before performing any operations.
     *
     * @param inventoryDTO the DTO containing inventory data to validate
     * @throws InventoryException if any required field is missing or invalid
     */
    private void validateInventoryData(InventoryDTO inventoryDTO) throws InventoryException {
        if (inventoryDTO.getQuantity() < 0) {
            throw new InventoryException("Quantity cannot be negative.");
        }
        if (inventoryDTO.getPrice() <= 0) {
            throw new InventoryException("Price must be greater than zero.");
        }
        if (inventoryDTO.getLocation() == null || inventoryDTO.getLocation().isEmpty()) {
            throw new InventoryException("Location is required.");
        }
        if (inventoryDTO.getLocation().length() > 255) {
            throw new InventoryException("Location should be less than 255 characters.");
        }
    }

    /**
     * Updates specific fields of an existing inventory item if new values are provided.
     *
     * @param existingInventory the existing inventory item to update
     * @param inventoryDTO      the DTO containing the new values for the inventory item
     */
    private void updateInventoryFields(Inventory existingInventory, InventoryDTO inventoryDTO) {
        if (inventoryDTO.getQuantity() >= 0 && inventoryDTO.getQuantity() != existingInventory.getQuantity()) {
            existingInventory.setQuantity(inventoryDTO.getQuantity());
        }
        if (inventoryDTO.getPrice() > 0 && inventoryDTO.getPrice() != existingInventory.getPrice()) {
            existingInventory.setPrice(inventoryDTO.getPrice());
        }
        if (inventoryDTO.getLocation() != null && !inventoryDTO.getLocation().equals(existingInventory.getLocation())) {
            existingInventory.setLocation(inventoryDTO.getLocation());
        }
    }
}
