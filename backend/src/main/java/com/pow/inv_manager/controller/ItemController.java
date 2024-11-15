package com.pow.inv_manager.controller;

import com.pow.inv_manager.dto.ItemDTO;
import com.pow.inv_manager.exception.ItemException;
import com.pow.inv_manager.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Endpoint to create a new item.
     * @param itemDTO the item data to be created
     * @return ResponseEntity containing the created item
     */
    @PostMapping("/create")
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDTO) {
        try {
            ItemDTO createdItem = itemService.createItem(itemDTO);
            return ResponseEntity.status(201).body(createdItem);
        } catch (ItemException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint to update an existing item by its ID.
     * @param id the ID of the item to update
     * @param itemDTO the updated item data
     * @return ResponseEntity containing the updated item
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        try {
            ItemDTO updatedItem = itemService.updateItem(id, itemDTO);
            return ResponseEntity.ok(updatedItem);
        } catch (ItemException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint to get an item by its ID.
     * @param id the ID of the item to retrieve
     * @return ResponseEntity containing the item
     */
    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        try {
            ItemDTO item = itemService.getItemById(id);
            return ResponseEntity.ok(item);
        } catch (ItemException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to list all items.
     * @return ResponseEntity containing the list of all items
     */
    @GetMapping("/all")
    public ResponseEntity<List<ItemDTO>> listItems() {
        List<ItemDTO> items = itemService.listItems();
        return ResponseEntity.ok(items);
    }

    /**
     * Endpoint to delete an item by its ID.
     * @param id the ID of the item to delete
     * @return ResponseEntity indicating the result of the operation
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.noContent().build();
        } catch (ItemException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
