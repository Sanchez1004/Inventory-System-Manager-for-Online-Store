package com.pow.inv_manager.model;

import com.pow.inv_manager.utils.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private Role role;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
