package com.lanka.user.repository;

import com.lanka.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByMobileOrEmail(String mobile, String email);

    Optional<User> findByEmailAndPasswordAndRole(String email, String password, String role);

    Optional<User> findByMobile(String mobile);

    Optional<User> findByEmail(String email);

    List<User> findByStatus(String status);
}
