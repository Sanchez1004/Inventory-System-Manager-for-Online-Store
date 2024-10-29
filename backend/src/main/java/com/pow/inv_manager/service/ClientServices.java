package com.pow.inv_manager.Service;

import com.pow.inv_manager.model.Client;
import com.pow.inv_manager.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> listClients() {
        return clientRepository.findAll();
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client clientDetails) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id " + id));

        existingClient.setid(clientDetails.getid());
        existingClient.setfirstName(clientDetails.getfirstName());
        existingClient.setlastName(clientDetails.getlastName());
        existingClient.setEmail(clientDetails.getemail());
        existingClient.sephone(clientDetails.getphone());
        existingClient.setpassword(clientDetails.getpassword());


        return clientRepository.save(existingClient);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public Optional<Client> getClientById(Long id) {
        return ClientRepository.findById(id);
    }
}