package com.pow.inv_manager.dto.mapper;

import com.pow.inv_manager.dto.ClientDTO;
import com.pow.inv_manager.model.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientMapper {
    public ClientDTO toDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .phone(client.getPhone())
                .address(client.getAddress())
                .email(client.getEmail())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .password(client.getPassword())
                .build();
    }

    public Client toEntity(ClientDTO clientDTO) {
        return Client.builder()
                .id(clientDTO.getId())
                .phone(clientDTO.getPhone())
                .address(clientDTO.getAddress())
                .email(clientDTO.getEmail())
                .firstName(clientDTO.getFirstName())
                .lastName(clientDTO.getLastName())
                .password(clientDTO.getPassword())
                .build();
    }
}
