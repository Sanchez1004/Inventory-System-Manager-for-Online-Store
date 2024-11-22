package com.pow.inv_manager.service.implementation;

import com.pow.inv_manager.dto.CustomerOrderDTO;
import com.pow.inv_manager.dto.InventoryDTO;
import com.pow.inv_manager.dto.ItemDTO;
import com.pow.inv_manager.dto.mapper.CustomerOrderMapper;
import com.pow.inv_manager.exception.OrderException;
import com.pow.inv_manager.model.CustomerOrder;
import com.pow.inv_manager.model.OrderItem;
import com.pow.inv_manager.repository.CustomerOrderRepository;
import com.pow.inv_manager.service.InventoryService;
import com.pow.inv_manager.service.ItemService;
import com.pow.inv_manager.service.OrderService;
import com.pow.inv_manager.utils.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CustomerOrderMapper customerOrderMapper;
    private final CustomerOrderRepository customerOrderRepository;
    private final InventoryService inventoryService;
    private final ItemService itemService;

    /**
     * Creates a new customer order based on the provided order DTO.
     *
     * @param customerOrderDTO The customer order details.
     * @return The created customer order DTO.
     */
    @Override
    @Transactional
    public CustomerOrderDTO createOrder(CustomerOrderDTO customerOrderDTO) {
        validateOrderItems(customerOrderDTO);
        CustomerOrder customerOrder = customerOrderMapper.toEntity(customerOrderDTO);
        customerOrder.calculateTotalAmount();

        return customerOrderMapper.toDTO(customerOrderRepository.save(customerOrder));
    }

    /**
     * Updates an existing customer order with the provided details.
     *
     * @param orderId           The ID of the order to update.
     * @param customerOrderDTO  The updated customer order details.
     * @return The updated customer order DTO.
     * @throws OrderException If there is an error during order update.
     */
    @Override
    @Transactional
    public CustomerOrderDTO updateOrder(Long orderId, CustomerOrderDTO customerOrderDTO) throws OrderException {
        CustomerOrder existingOrder = getOrderEntity(orderId);

        // Check if the client is the same
        if (!existingOrder.getClient().equals(customerOrderDTO.getClient())) {
            throw new OrderException("Cannot change the client of an existing order.");
        }

        validateOrderItems(customerOrderDTO);
        List<OrderItem> updatedItems = customerOrderDTO.getOrderItems();

        // Iterate through the existing items to check which need to be modified or deleted
        for (OrderItem existingItem : existingOrder.getOrderItems()) {
            Optional<OrderItem> updatedItem = updatedItems.stream()
                    .filter(item -> item.getId().equals(existingItem.getId()))
                    .findFirst();

            if (updatedItem.isPresent()) {
                OrderItem itemToUpdate = updatedItem.get();
                existingItem.setQuantity(itemToUpdate.getQuantity());
                existingItem.setUnitPrice(itemToUpdate.getUnitPrice());
                existingItem.setSubtotal(itemToUpdate.getSubtotal());
            } else {
                existingOrder.getOrderItems().remove(existingItem);
            }
        }

        for (OrderItem newItem : updatedItems) {
            if (newItem.getId() == null) {
                newItem.setCustomerOrder(existingOrder);
                existingOrder.getOrderItems().add(newItem);
            }
        }

        existingOrder.calculateTotalAmount();

        return customerOrderMapper.toDTO(customerOrderRepository.save(existingOrder));
    }

    /**
     * Retrieves a customer order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The corresponding customer order DTO.
     * @throws OrderException If the order is not found.
     */
    @Override
    public CustomerOrderDTO getOrder(Long orderId) throws OrderException {
        CustomerOrder order = getOrderEntity(orderId);
        return customerOrderMapper.toDTO(order);
    }

    /**
     * Retrieves all customer orders.
     *
     * @return A list of all customer order DTOs.
     */
    @Override
    public List<CustomerOrderDTO> getAllOrders() {
        List<CustomerOrder> orders = customerOrderRepository.findAll();
        return orders.stream()
                .map(customerOrderMapper::toDTO)
                .toList();
    }

    /**
     * Updates the status of an existing order.
     *
     * @param orderId The ID of the order to update.
     * @param status  The new status to set.
     * @throws OrderException If the order is not found or there is an error during the update.
     */
    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus status) throws OrderException {
        CustomerOrder order = getOrderEntity(orderId);
        order.setStatus(status);
        customerOrderRepository.save(order);
    }

    /**
     * Retrieves all orders for a specific user.
     *
     * @param userId The ID of the user whose orders are to be retrieved.
     * @return A list of customer order DTOs.
     */
    @Override
    public List<CustomerOrderDTO> getUserOrders(Long userId) {
        List<CustomerOrder> orders = customerOrderRepository.findByClientId(userId);
        return orders.stream()
                .map(customerOrderMapper::toDTO)
                .toList();
    }

    /**
     * Deletes a customer order by its ID.
     *
     * @param orderId The ID of the order to delete.
     * @throws OrderException If the order is not found or there is an error during deletion.
     */
    @Override
    @Transactional
    public void deleteOrder(Long orderId) throws OrderException {
        CustomerOrder order = getOrderEntity(orderId);
        customerOrderRepository.delete(order);
    }

    /**
     * Confirms the order, typically transitioning its status and performing any additional checks.
     *
     * @param orderId The ID of the order to confirm.
     * @throws OrderException If there is an error during order confirmation.
     */
    @Override
    @Transactional
    public void confirmOrder(Long orderId) throws OrderException {
        CustomerOrder order = getOrderEntity(orderId);
        order.setStatus(OrderStatus.CONFIRMED);
        customerOrderRepository.save(order);
    }

    /**
     * Retrieves the customer order entity by its ID, throwing an exception if not found.
     *
     * @param orderId The ID of the order.
     * @return The customer order entity.
     * @throws OrderException If the order is not found.
     */
    private CustomerOrder getOrderEntity(Long orderId) throws OrderException {
        return customerOrderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found with id: " + orderId));
    }

    /**
     * Validates the order items, ensuring that the quantities are available in inventory.
     *
     * @param customerOrderDTO The customer order DTO
     */
    @SneakyThrows
    private void validateOrderItems(CustomerOrderDTO customerOrderDTO) {
        for (OrderItem orderItem : customerOrderDTO.getOrderItems()) {
            ItemDTO item = itemService.getItemById(orderItem.getInventory().getItem().getId());
            InventoryDTO inventory = inventoryService.getInventoryById(item.getId());

            if (inventory.getQuantity() < orderItem.getQuantity() && !orderItem.getInventory().getIsActive()) {
                throw new OrderException("Not enough stock for item: " + item.getName());
            }

            orderItem.setUnitPrice(inventory.getItem().getPrice());
        }
    }
}
