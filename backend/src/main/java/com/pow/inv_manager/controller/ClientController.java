package com.pow.inv_manager.controller;

import com.pow.inv_manager.dto.ClientDTO;
import com.pow.inv_manager.exception.ClientException;
import com.pow.inv_manager.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Endpoint to create a new client.
     * @param clientDTO the client data to be created
     * @return ResponseEntity containing the created client
     */
    @PostMapping("/create")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        try {
            ClientDTO createdClient = clientService.createClient(clientDTO);
            return ResponseEntity.ok(createdClient);
        } catch (ClientException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint to update an existing client by their ID.
     * @param id the ID of the client to update
     * @param clientDTO the updated client data
     * @return ResponseEntity containing the updated client
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        try {
            ClientDTO updatedClient = clientService.updateClient(id, clientDTO);
            return ResponseEntity.ok(updatedClient);
        } catch (ClientException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint to retrieve a client by their ID.
     * @param id the ID of the client to retrieve
     * @return ResponseEntity containing the client information
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        try {
            ClientDTO client = clientService.getClient(id);
            return ResponseEntity.ok(client);
        } catch (ClientException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to retrieve a list of all clients.
     * @return ResponseEntity containing the list of all clients
     */
    @GetMapping("/all")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    /**
     * Endpoint to delete a client by their ID.
     * @param id the ID of the client to delete
     * @return ResponseEntity indicating the result of the delete operation
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }
}
