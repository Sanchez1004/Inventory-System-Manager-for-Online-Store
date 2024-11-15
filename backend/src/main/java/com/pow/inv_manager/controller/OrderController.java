package com.pow.inv_manager.controller;

import com.pow.inv_manager.dto.CustomerOrderDTO;
import com.pow.inv_manager.exception.OrderException;
import com.pow.inv_manager.rabbitmq.MessagePublisherService;
import com.pow.inv_manager.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/orders")
public class OrderController {

    private final MessagePublisherService messagePublisherService;
    private final OrderService orderService;

    public OrderController(MessagePublisherService messagePublisherService, OrderService orderService) {
        this.messagePublisherService = messagePublisherService;
        this.orderService = orderService;
    }

//    @PostMapping("/create-order")
//    public ResponseEntity<String> createOrder(@RequestBody CustomerOrderDTO customerOrderDTO) {
//        messagePublisherService.sendOrderToQueue(customerOrderDTO);
//        return ResponseEntity.ok("Order received and added to processing queue.");
//    }
    @PostMapping("/create-order")
    public ResponseEntity<CustomerOrderDTO> createOrder(@RequestBody CustomerOrderDTO customerOrderDTO) {
        try {
            return ResponseEntity.ok(orderService.createOrder(customerOrderDTO));
        } catch (OrderException e) {
            throw new RuntimeException(e);
        }
    }
}
