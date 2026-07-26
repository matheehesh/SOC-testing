package com.lanka.user.controller;

import com.lanka.user.dto.*;
import com.lanka.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    RegisterResponse register(@RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/login")
    LoginResponse login(@RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/admin/login")
    AdminLoginResponse adminLogin(@RequestBody AdminLoginRequest request) {
        return service.adminLogin(request);
    }

    @GetMapping("/users/pending")
    List<UserApprovalResponse> pendingUsers() {
        return service.getPendingUsers();
    }

    @PutMapping("/users/{id}/approve")
    UserApprovalResponse approveUser(@PathVariable Long id) {
        return service.approveUser(id);
    }

    @PutMapping("/users/{id}/reject")
    UserApprovalResponse rejectUser(@PathVariable Long id) {
        return service.rejectUser(id);
    }
}
