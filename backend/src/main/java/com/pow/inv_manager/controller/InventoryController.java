package com.pow.inv_manager.controller;

import com.pow.inv_manager.service.InventoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {this.inventoryService = inventoryService;}

}
