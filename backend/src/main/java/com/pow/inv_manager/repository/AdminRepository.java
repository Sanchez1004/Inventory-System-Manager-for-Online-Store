package com.pow.inv_manager.repository;

import com.pow.inv_manager.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Object> findByEmail(String email);
}
