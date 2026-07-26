package com.lanka.user.config;

import com.lanka.user.model.Admin;
import com.lanka.user.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {
    private final AdminRepository admins;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(AdminRepository admins, PasswordEncoder passwordEncoder) {
        this.admins = admins;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Admin admin = admins.findByGmail("admin@gmail.com").orElseGet(Admin::new);
        if (admin.getId() == null) {
            admin.setGmail("admin@gmail.com");
        }
        admin.setName("System Admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admins.save(admin);
    }
}
