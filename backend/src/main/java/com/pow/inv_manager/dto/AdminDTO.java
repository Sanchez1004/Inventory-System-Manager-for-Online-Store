package com.pow.inv_manager.dto;

import com.pow.inv_manager.model.Address;
import com.pow.inv_manager.utils.Role;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private Role role;
    private Address address;
}
