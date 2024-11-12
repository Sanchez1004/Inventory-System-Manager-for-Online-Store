package com.pow.inv_manager.dto.mapper;

import com.pow.inv_manager.dto.AddressDTO;
import com.pow.inv_manager.model.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper {

    public AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .city(address.getCity())
                .country(address.getCountry())
                .street(address.getStreet())
                .build();
    }

    public Address toEntity(AddressDTO addressDTO) {
        return Address.builder()
                .id(addressDTO.getId())
                .city(addressDTO.getCity())
                .country(addressDTO.getCountry())
                .street(addressDTO.getStreet())
                .build();
    }
}
