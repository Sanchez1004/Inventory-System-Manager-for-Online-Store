package com.pow.inv_manager.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pow.inv_manager.dto.CustomerOrderDTO;
import com.pow.inv_manager.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderListener {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    public OrderListener(OrderService orderService, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ORDER)
    public void handleOrderMessage(String message) {
        try {
            log.info("Received order from RabbitMQ: {}", message);
            CustomerOrderDTO customerOrderDTO = objectMapper.readValue(message, CustomerOrderDTO.class);

            // Validate immediately after deserialization
            if (customerOrderDTO.getOrderItems() == null || customerOrderDTO.getOrderItems().isEmpty()) {
                log.error("Invalid order: missing details.");
                return; // Or send to a dead-letter queue
            }

            CustomerOrderDTO processedOrder = orderService.createOrder(customerOrderDTO);
            log.info("Order processed successfully: {}", processedOrder);
        } catch (Exception e) {
            log.error("Failed to process order message: {}", message, e);
        }
    }
}
