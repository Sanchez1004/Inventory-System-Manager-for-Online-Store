package com.pow.inv_manager.controller;

import com.pow.inv_manager.dto.CustomerOrderDTO;
import com.pow.inv_manager.exception.OrderException;
import com.pow.inv_manager.service.OrderService;
import com.pow.inv_manager.utils.OrderStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint to create a new order.
     * @param customerOrderDTO the order data to be created
     * @return ResponseEntity containing the created order
     */
    @PostMapping("/create")
    public ResponseEntity<CustomerOrderDTO> createOrder(@RequestBody CustomerOrderDTO customerOrderDTO) {
        try {
            CustomerOrderDTO createdOrder = orderService.createOrder(customerOrderDTO);
            return ResponseEntity.ok(createdOrder);
        } catch (OrderException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint to update an existing order by its ID.
     * @param id the ID of the order to update
     * @param customerOrderDTO the updated order data
     * @return ResponseEntity containing the updated order
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerOrderDTO> updateOrder(@PathVariable Long id, @RequestBody CustomerOrderDTO customerOrderDTO) {
        try {
            CustomerOrderDTO updatedOrder = orderService.updateOrder(id, customerOrderDTO);
            return ResponseEntity.ok(updatedOrder);
        } catch (OrderException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint to retrieve an order by its ID.
     * @param id the ID of the order to retrieve
     * @return ResponseEntity containing the order information
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrderDTO> getOrder(@PathVariable Long id) {
        try {
            CustomerOrderDTO order = orderService.getOrder(id);
            return ResponseEntity.ok(order);
        } catch (OrderException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to retrieve a list of all orders.
     * @return ResponseEntity containing the list of all orders
     */
    @GetMapping("/all")
    public ResponseEntity<List<CustomerOrderDTO>> getAllOrders() throws OrderException {
        List<CustomerOrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Endpoint to delete an order by its ID.
     * @param id the ID of the order to delete
     * @return ResponseEntity indicating the result of the delete operation
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (OrderException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint to update the status of an order.
     * @param id the ID of the order to update
     * @param status the new status for the order
     * @return ResponseEntity indicating the result of the status update
     */
    @PatchMapping("/update-status/{id}")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        try {
            orderService.updateOrderStatus(id, status);
            return ResponseEntity.noContent().build();
        } catch (OrderException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint to retrieve all orders for a specific user.
     * @param userId the ID of the user whose orders are to be retrieved
     * @return ResponseEntity containing the list of user orders
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CustomerOrderDTO>> getUserOrders(@PathVariable Long userId) throws OrderException {
        List<CustomerOrderDTO> userOrders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(userOrders);
    }

    /**
     * Endpoint to confirm an order by its ID.
     * @param id the ID of the order to confirm
     * @return ResponseEntity indicating the result of the confirmation
     */
    @PostMapping("/confirm/{id}")
    public ResponseEntity<Void> confirmOrder(@PathVariable Long id) {
        try {
            orderService.confirmOrder(id);
            return ResponseEntity.noContent().build();
        } catch (OrderException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
