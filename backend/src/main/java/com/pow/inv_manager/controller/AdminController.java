package com.pow.inv_manager.controller;

import com.pow.inv_manager.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/admins")
public class AdminController {

    public AdminController(AdminService adminService) {
    }

}
