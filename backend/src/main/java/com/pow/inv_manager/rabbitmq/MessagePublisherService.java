package com.pow.inv_manager.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pow.inv_manager.dto.CustomerOrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessagePublisherService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void sendOrderToQueue(CustomerOrderDTO customerOrderDTO) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(customerOrderDTO);
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_ORDER, jsonMessage);
            log.info("Order sent successfully: {}", jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert OrderDTO to JSON: {}", customerOrderDTO, e);
        }
    }
}

