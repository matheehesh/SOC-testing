package com.lanka.broker.repository;

import com.lanka.broker.model.OfflineWorker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfflineWorkerRepository extends JpaRepository<OfflineWorker, Long> {
    List<OfflineWorker> findByBrokerEntityId(Long brokerEntityId);
}
