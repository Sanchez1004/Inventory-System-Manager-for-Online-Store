package com.pow.inv_manager.controller;

import com.pow.inv_manager.dto.AdminDTO;
import com.pow.inv_manager.dto.AuthResponse;
import com.pow.inv_manager.dto.LoginRequest;
import com.pow.inv_manager.exception.AdminException;
import com.pow.inv_manager.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Endpoint to create a new admin.
     * @param loginRequest the admin data to be logged in
     * @return ResponseEntity containing the login credentials
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse credentials = adminService.login(loginRequest);
        return ResponseEntity.ok(credentials);
    }

    /**
     * Endpoint to create a new admin.
     * @param adminDTO the admin data to be registered
     * @return ResponseEntity containing the login credentials
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AdminDTO adminDTO) {
        AuthResponse credentials = adminService.register(adminDTO);
        return ResponseEntity.ok(credentials);
    }

    /**
     * Endpoint to create a new admin.
     * @param adminDTO the admin data to be created
     * @return ResponseEntity containing the created admin
     */
    @PostMapping("/create")
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminDTO adminDTO) {
        try {
            AdminDTO createdAdmin = adminService.createAdmin(adminDTO);
            return ResponseEntity.ok(createdAdmin);
        } catch (AdminException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint to update an existing admin by their ID.
     * @param id the ID of the admin to update
     * @param adminDTO the updated admin data
     * @return ResponseEntity containing the updated admin
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Long id, @RequestBody AdminDTO adminDTO) {
        try {
            AdminDTO updatedAdmin = adminService.updateAdmin(id, adminDTO);
            return ResponseEntity.ok(updatedAdmin);
        } catch (AdminException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint to retrieve an admin by their ID.
     * @param id the ID of the admin to retrieve
     * @return ResponseEntity containing the admin information
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdmin(@PathVariable Long id) {
        try {
            AdminDTO admin = adminService.getAdmin(id);
            return ResponseEntity.ok(admin);
        } catch (AdminException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to retrieve a list of all admins.
     * @return ResponseEntity containing the list of all admins
     */
    @GetMapping("/all")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    /**
     * Endpoint to delete an admin by their ID.
     * @param id the ID of the admin to delete
     * @return ResponseEntity indicating the result of the delete operation
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdminById(id);
        return ResponseEntity.noContent().build();
    }
}
