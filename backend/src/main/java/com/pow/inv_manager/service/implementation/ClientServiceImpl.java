package com.pow.inv_manager.service.implementation;

import com.pow.inv_manager.dto.ClientDTO;
import com.pow.inv_manager.dto.mapper.AddressMapper;
import com.pow.inv_manager.dto.mapper.ClientMapper;
import com.pow.inv_manager.exception.ClientException;
import com.pow.inv_manager.model.Client;
import com.pow.inv_manager.repository.ClientRepository;
import com.pow.inv_manager.service.AddressService;
import com.pow.inv_manager.service.ClientService;
import com.pow.inv_manager.service.OrderService;
import com.pow.inv_manager.utils.Role;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private static final String CLIENT_NOT_FOUND_MESSAGE = "Client not found with ID: ";

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final AddressService addressService;
    private final AddressMapper addressMapper;
    private final OrderService orderService;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, AddressService addressService, AddressMapper addressMapper, OrderService orderService) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.addressService = addressService;
        this.addressMapper = addressMapper;
        this.orderService = orderService;
    }

    /**
     * Registers a new client along with an address.
     *
     * @param clientDTO the data transfer object containing the client's information
     * @return the saved client information as a DTO
     */
    @SneakyThrows
    @Override
    @Transactional
    public ClientDTO createClient(ClientDTO clientDTO) {
        validateClientData(clientDTO);

        if (clientRepository.existsById(clientDTO.getId())) {
            throw new ClientException("Client already exists with ID: " + clientDTO.getId());
        }

        // Manage Address creation
        addressService.createAddress(addressMapper.toDTO(clientDTO.getAddress()));

        Client clientEntity = clientMapper.toEntity(clientDTO);
        clientEntity.setRole(Role.USER.toString());
        clientEntity.setAddress(clientDTO.getAddress());
        Client savedClient = clientRepository.save(clientEntity);
        return clientMapper.toDTO(savedClient);
    }

    /**
     * Updates an existing client profile, including address if provided.
     *
     * @param clientDTO the data transfer object containing updated client information
     * @param id        the ID of the client to update
     * @return the updated client information as a DTO
     * @throws ClientException if the client does not exist or data is invalid
     */
    @Override
    @Transactional
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) throws ClientException {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ClientException(CLIENT_NOT_FOUND_MESSAGE + id));

        validateClientData(clientDTO);
        updateClientFields(existingClient, clientDTO);

        Client updatedClient = clientRepository.save(existingClient);
        return clientMapper.toDTO(updatedClient);
    }

    /**
     * Updates specific fields of an existing client if new values are provided.
     *
     * @param existingClient the current client entity
     * @param clientDTO      the data transfer object containing new client information
     */
    @SneakyThrows
    private void updateClientFields(Client existingClient, ClientDTO clientDTO) {
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
        if (clientDTO.getPassword() != null && !clientDTO.getPassword().equals(existingClient.getPassword())) {
            existingClient.setPassword(clientDTO.getPassword());
        }
        if (clientDTO.getAddress() != null && !clientDTO.getAddress().equals(existingClient.getAddress())) {
            existingClient.setAddress(clientDTO.getAddress());
            addressService.updateAddress(existingClient.getAddress().getId(), addressMapper.toDTO(existingClient.getAddress()));
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
                .orElseThrow(() -> new ClientException(CLIENT_NOT_FOUND_MESSAGE + id));
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
                .toList();
    }

    /**
     * Delete client by id
     * @param id the unique identifier of the client
     *
     */
    @Override
    @SneakyThrows
    @Transactional
    public void deleteClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientException(CLIENT_NOT_FOUND_MESSAGE + id));

        orderService.deleteOrderByClientId(id);
        clientRepository.delete(client);
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
        if (clientDTO.getPassword() == null || clientDTO.getPassword().isEmpty()) {
            throw new ClientException("Client's password is required");
        }
    }
}
