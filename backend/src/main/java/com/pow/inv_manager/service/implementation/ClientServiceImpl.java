package com.pow.inv_manager.service.implementation;

import com.pow.inv_manager.dto.ClientDTO;
import com.pow.inv_manager.dto.mapper.ClientMapper;
import com.pow.inv_manager.model.Client;
import com.pow.inv_manager.exception.ClientException;
import com.pow.inv_manager.repository.ClientRepository;
import com.pow.inv_manager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    /**
     * Registers a new client.
     *
     * @param clientDTO the data transfer object containing the client's information
     * @return the saved client information as a DTO
     * @throws ClientException if the data is invalid or client already exists
     */
    @Override
    public ClientDTO createClient(ClientDTO clientDTO) throws ClientException {
        validateClientData(clientDTO);

        if (clientRepository.existsById(clientDTO.getId())) {
            throw new ClientException("Client already exists with ID: " + clientDTO.getId());
        }

        Client clientEntity = clientMapper.toEntity(clientDTO);
        Client savedClient = clientRepository.save(clientEntity);
        return clientMapper.toDTO(savedClient);
    }

    /**
     * Updates an existing client profile.
     *
     * @param clientDTO the data transfer object containing updated client information
     * @param id        the ID of the client to update
     * @return the updated client information as a DTO
     * @throws ClientException if the client does not exist or data is invalid
     */
    @Override
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) throws ClientException {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ClientException("Client not found with ID: " + id));

        validateClientData(clientDTO);
        updateClientFields(existingClient, clientDTO);

        Client updatedClient = clientRepository.save(existingClient);
        return clientMapper.toDTO(updatedClient);
    }

    /**
     * Updates the fields of an existing client only if the new values are different.
     *
     * @param existingClient the current client entity
     * @param clientDTO      the data transfer object containing new client information
     */
    private void updateClientFields(Client existingClient, ClientDTO clientDTO) {
        if (clientDTO.getId() != null && !clientDTO.getId().equals(existingClient.getId())) {
            existingClient.setId(clientDTO.getId());
        }
        if (clientDTO.getFirstName() != null && !clientDTO.getFirstName().equals(existingClient.getFirstName())) {
            existingClient.setFirstName(clientDTO.getFirstName());
        }
        if (clientDTO.getLastName() != null && !clientDTO.getLastName().equals(existingClient.getLastName())) {
            existingClient.setLastName(clientDTO.getLastName());
        }
        if (clientDTO.getEmail() != null && !clientDTO.getEmail().equals(existingClient.getEmail())) {
            existingClient.setEmail(clientDTO.getEmail());
        }
        if (clientDTO.getPhone() != null && !clientDTO.getPhone().equals(existingClient.getPhone())) {
            existingClient.setPhone(clientDTO.getPhone());
        }
        if (clientDTO.getAddress() != null && !clientDTO.getAddress().equals(existingClient.getAddress())) {
            existingClient.setAddress(clientDTO.getAddress());
        }
        if (clientDTO.getPassword() != null && !clientDTO.getPassword().equals(existingClient.getPassword())) {
            existingClient.setPassword(clientDTO.getPassword());
        }
    }

    /**
     * Retrieves a client's profile by ID.
     *
     * @param id the unique identifier of the client
     * @return the client profile as a DTO
     * @throws ClientException if the client does not exist
     */
    @Override
    public ClientDTO getClient(Long id) throws ClientException {
        return clientRepository.findById(id)
                .map(clientMapper::toDTO)
                .orElseThrow(() -> new ClientException("Client not found with ID: " + id));
    }

    /**
     * Lists all clients with optional pagination.
     *
     * @return a list of all clients as DTOs
     */
    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Validates essential client data fields.
     *
     * @param clientDTO the client data transfer object to validate
     * @throws ClientException if any required field is missing or invalid
     */
    private void validateClientData(ClientDTO clientDTO) throws ClientException {
        if (clientDTO.getFirstName() == null || clientDTO.getFirstName().isEmpty()) {
            throw new ClientException("Client's first name is required");
        }
        if (clientDTO.getEmail() == null || clientDTO.getEmail().isEmpty()) {
            throw new ClientException("Client's email is required");
        }
        if (clientDTO.getAddress() == null) {
            throw new ClientException("Client's address is required");
        }
    }
}
