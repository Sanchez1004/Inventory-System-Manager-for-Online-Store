package com.pow.inv_manager.controller;

import com.pow.inv_manager.dto.AddressDTO;
import com.pow.inv_manager.exception.AddressException;
import com.pow.inv_manager.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * Endpoint to create a new address.
     * @param addressDTO the address data to be created
     * @return ResponseEntity containing the created address
     */
    @PostMapping("/create")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO) {
        try {
            AddressDTO createdAddress = addressService.createAddress(addressDTO);
            return ResponseEntity.status(201).body(createdAddress);
        } catch (AddressException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint to update an existing address by its ID.
     * @param id the ID of the address to update
     * @param addressDTO the updated address data
     * @return ResponseEntity containing the updated address
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        try {
            AddressDTO updatedAddress = addressService.updateAddress(id, addressDTO);
            return ResponseEntity.ok(updatedAddress);
        } catch (AddressException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint to get an address by its ID.
     * @param id the ID of the address to retrieve
     * @return ResponseEntity containing the address
     */
    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Long id) {
        try {
            AddressDTO address = addressService.getAddress(id);
            return ResponseEntity.ok(address);
        } catch (AddressException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to get all addresses.
     * @return ResponseEntity containing the list of all addresses
     */
    @GetMapping("/all")
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        List<AddressDTO> addressList = addressService.getAllAddresses();
        return ResponseEntity.ok(addressList);
    }

    /**
     * Endpoint to delete an address by its ID.
     * @param id the ID of the address to delete
     * @return ResponseEntity indicating the result of the operation
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        try {
            addressService.deleteAddress(id);
            return ResponseEntity.noContent().build();
        } catch (AddressException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
