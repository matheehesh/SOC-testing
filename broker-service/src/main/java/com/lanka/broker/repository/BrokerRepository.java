package com.lanka.broker.repository;

import com.lanka.broker.model.Broker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrokerRepository extends JpaRepository<Broker, Long> {
    Optional<Broker> findByBrokerId(String brokerId);

    Optional<Broker> findByEmail(String email);

    Optional<Broker> findByPhone(String phone);

    List<Broker> findByStatus(String status);

    long countByStatus(String status);
}
