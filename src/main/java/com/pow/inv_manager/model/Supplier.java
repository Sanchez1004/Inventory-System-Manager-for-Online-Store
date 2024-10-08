package com.pow.inv_manager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Supplier {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String companyName;
    private String email;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
