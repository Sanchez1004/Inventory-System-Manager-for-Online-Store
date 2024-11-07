package com.pow.inv_manager.dto;

import com.pow.inv_manager.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String companyName;
    private String email;
    private Address address;
}