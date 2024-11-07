package com.pow.inv_manager.service;

import java.util.List;

import com.pow.inv_manager.dto.ClientDTO;
import com.pow.inv_manager.exception.ClientException;

public interface ClientService {
    ClientDTO createClient(ClientDTO clientDTO) throws ClientException;

    ClientDTO updateClient(Long clientId, ClientDTO clientDTO) throws ClientException;

    ClientDTO getClient(Long clientId) throws ClientException;

    List<ClientDTO> getAllClients() throws ClientException;
}
