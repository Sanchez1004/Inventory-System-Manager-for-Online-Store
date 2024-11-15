package com.pow.inv_manager.service;

import com.pow.inv_manager.dto.AddressDTO;
import com.pow.inv_manager.exception.AddressException;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO) throws AddressException;
    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) throws AddressException;
    AddressDTO getAddress(Long addressId) throws AddressException;
    List<AddressDTO> getAllAddresses();
    void deleteAddress(Long id) throws AddressException;
}
