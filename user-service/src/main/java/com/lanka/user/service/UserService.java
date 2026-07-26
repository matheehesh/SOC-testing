package com.lanka.user.service;

import com.lanka.user.dto.*;
import com.lanka.user.model.Admin;
import com.lanka.user.model.User;
import com.lanka.user.repository.AdminRepository;
import com.lanka.user.repository.UserRepository;
import com.lanka.user.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository users;
    private final AdminRepository admins;
    private final JwtUtil jwt;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository users, AdminRepository admins, JwtUtil jwt, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.admins = admins;
        this.jwt = jwt;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse register(RegisterRequest request) {
        String email = normalizeEmail(request.email());
        String mobile = normalizeText(request.mobile());
        if ((email == null && mobile == null) || request.password() == null || request.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or mobile and password are required");
        }
        User user = email != null
                ? users.findByEmail(email).orElseGet(User::new)
                : mobile != null ? users.findByMobile(mobile).orElseGet(User::new) : new User();
        user.setName(normalizeText(request.name()));
        user.setMobile(mobile);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(normalizeRole(request.role()));
        user.setDistrict(normalizeText(request.district()));
        user.setCity(normalizeText(request.city()));
        if (user.getStatus() == null || "REJECTED".equalsIgnoreCase(user.getStatus())) {
            user.setStatus("PENDING");
        }
        User saved = users.save(user);
        return new RegisterResponse(saved.getId(), "User registered and waiting for admin approval", saved.getStatus());
    }

    public LoginResponse login(LoginRequest request) {
        String email = normalizeEmail(request.email());
        String mobile = normalizeText(request.mobile());
        String role = normalizeRole(request.role());
        if ((email == null && mobile == null) || request.password() == null || request.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or mobile and password are required");
        }
        Optional<User> found = email != null ? users.findByEmail(email) : users.findByMobile(mobile);
        User user = found.filter(u -> role.equalsIgnoreCase(u.getRole()))
                .filter(u -> passwordEncoder.matches(request.password(), u.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid login credentials"));
        return new LoginResponse(jwt.generateToken(user.getEmail() != null ? user.getEmail() : user.getMobile(), user.getRole()), user.getRole(), user.getName(), user.getId(), user.getStatus(), user.getDistrict(), user.getCity());
    }

    public AdminLoginResponse adminLogin(AdminLoginRequest request) {
        Admin admin = admins.findByGmail(request.gmail())
                .filter(a -> passwordEncoder.matches(request.password(), a.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid admin credentials"));
        return new AdminLoginResponse(jwt.generateToken(admin.getGmail(), "ADMIN"), admin.getName(), "ADMIN");
    }

    public List<UserApprovalResponse> getPendingUsers() {
        return users.findByStatus("PENDING").stream().map(this::toApprovalResponse).toList();
    }

    public UserApprovalResponse approveUser(Long id) {
        User user = findUser(id);
        user.setStatus("APPROVED");
        return toApprovalResponse(users.save(user));
    }

    public UserApprovalResponse rejectUser(Long id) {
        User user = findUser(id);
        user.setStatus("REJECTED");
        return toApprovalResponse(users.save(user));
    }

    private User findUser(Long id) {
        return users.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private UserApprovalResponse toApprovalResponse(User user) {
        return new UserApprovalResponse(user.getId(), user.getName(), user.getEmail(), user.getMobile(), user.getRole(), user.getDistrict(), user.getCity(), user.getStatus());
    }

    private String normalizeEmail(String value) {
        String normalized = normalizeText(value);
        return normalized == null ? null : normalized.toLowerCase();
    }

    private String normalizeRole(String value) {
        String normalized = normalizeText(value);
        return normalized == null ? "" : normalized.toUpperCase();
    }

    private String normalizeText(String value) {
        if (value == null || value.isBlank()) return null;
        return value.trim();
    }
}
