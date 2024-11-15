package com.pow.inv_manager.service.implementation;

import com.pow.inv_manager.dto.AddressDTO;
import com.pow.inv_manager.dto.mapper.AddressMapper;
import com.pow.inv_manager.exception.AddressException;
import com.pow.inv_manager.model.Address;
import com.pow.inv_manager.repository.AddressRepository;
import com.pow.inv_manager.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private static final String ADDRESS_NOT_FOUND_MESSAGE = "Address not found with ID: ";

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    @Transactional
    public AddressDTO createAddress(AddressDTO addressDTO) throws AddressException {
        Address address = addressMapper.toEntity(addressDTO);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.toDTO(savedAddress);
    }

    @Override
    @Transactional
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) throws AddressException {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressException(ADDRESS_NOT_FOUND_MESSAGE + addressId));

        existingAddress.setCity(addressDTO.getCity());
        existingAddress.setCountry(addressDTO.getCountry());
        existingAddress.setStreet(addressDTO.getStreet());

        Address updatedAddress = addressRepository.save(existingAddress);
        return addressMapper.toDTO(updatedAddress);
    }

    @Override
    public AddressDTO getAddress(Long addressId) throws AddressException {
        return addressRepository.findById(addressId)
                .map(addressMapper::toDTO)
                .orElseThrow(() -> new AddressException(ADDRESS_NOT_FOUND_MESSAGE + addressId));
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(addressMapper::toDTO)
                .toList();
    }

    public void deleteAddress(Long addressId) throws AddressException {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressException(ADDRESS_NOT_FOUND_MESSAGE + addressId));

        addressRepository.delete(address);
    }
}
