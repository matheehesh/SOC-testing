package com.lanka.broker.service;

import com.lanka.broker.dto.*;
import com.lanka.broker.model.Broker;
import com.lanka.broker.model.OfflineWorker;
import com.lanka.broker.repository.BrokerRepository;
import com.lanka.broker.repository.OfflineWorkerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class BrokerService {
    private final BrokerRepository brokers;
    private final OfflineWorkerRepository workers;
    private final PasswordEncoder passwordEncoder;

    public BrokerService(BrokerRepository brokers, OfflineWorkerRepository workers, PasswordEncoder passwordEncoder) {
        this.brokers = brokers;
        this.workers = workers;
        this.passwordEncoder = passwordEncoder;
    }

    public BrokerResponse apply(BrokerRequest request) {
        if (request.password() == null || request.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Broker password is required");
        }
        Broker broker = new Broker();
        broker.setName(request.name());
        broker.setNic(request.nic());
        broker.setPhone(request.phone());
        broker.setEmail(request.email());
        broker.setPassword(passwordEncoder.encode(request.password()));
        broker.setDistrict(request.district());
        broker.setCity(request.city());
        broker.setYearsExperience(request.yearsExperience());
        broker.setEstimatedWorkers(request.estimatedWorkers());
        broker.setWorkerMethod(request.workerMethod());
        broker.setStatus("PENDING");
        return toResponse(brokers.save(broker));
    }

    public BrokerLoginResponse login(BrokerLoginRequest request) {
        if (request.password() == null || request.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Broker password is required");
        }
        Broker broker = request.email() != null && !request.email().isBlank()
                ? brokers.findByEmail(request.email()).orElse(null)
                : brokers.findByPhone(request.phone()).orElse(null);
        if (broker == null || broker.getPassword() == null || !passwordEncoder.matches(request.password(), broker.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid broker login credentials");
        }
        if (!"APPROVED".equalsIgnoreCase(broker.getStatus())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Broker account is not approved");
        }
        return new BrokerLoginResponse(broker.getId(), broker.getBrokerId(), broker.getName(), broker.getEmail(), broker.getPhone(), broker.getDistrict(), broker.getCity(), broker.getStatus());
    }

    public List<BrokerResponse> list() {
        return brokers.findAll().stream().map(this::toResponse).toList();
    }

    public List<BrokerResponse> getPending() {
        return brokers.findByStatus("PENDING").stream().map(this::toResponse).toList();
    }

    public BrokerResponse approve(Long id) {
        Broker broker = findBroker(id);
        broker.setStatus("APPROVED");
        if (broker.getBrokerId() == null || broker.getBrokerId().isBlank()) {
            broker.setBrokerId(String.format("BRK-%04d", brokers.count() + 1));
        }
        broker.setReviewedAt(LocalDateTime.now());
        return toResponse(brokers.save(broker));
    }

    public BrokerResponse reject(Long id) {
        Broker broker = findBroker(id);
        broker.setStatus("REJECTED");
        broker.setReviewedAt(LocalDateTime.now());
        return toResponse(brokers.save(broker));
    }

    public OfflineWorkerResponse addOfflineWorker(String brokerId, OfflineWorkerRequest request) {
        Broker broker = brokers.findByBrokerId(brokerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Broker not found"));
        if (!"APPROVED".equalsIgnoreCase(broker.getStatus()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Broker is not approved");
        OfflineWorker worker = new OfflineWorker();
        worker.setBrokerId(broker.getBrokerId());
        worker.setBrokerEntityId(broker.getId());
        worker.setWorkerName(request.workerName());
        worker.setWorkerNic(request.workerNic());
        worker.setDistrict(broker.getDistrict());
        worker.setCity(broker.getCity());
        worker.setSkills(request.skills());
        worker.setAvailability(request.availability());
        OfflineWorker saved = workers.save(worker);
        broker.setTotalWorkers(workers.findByBrokerEntityId(broker.getId()).size());
        brokers.save(broker);
        return toWorkerResponse(saved);
    }

    public List<OfflineWorkerResponse> getWorkers(String brokerId) {
        Broker broker = brokers.findByBrokerId(brokerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Broker not found"));
        return workers.findByBrokerEntityId(broker.getId()).stream().map(this::toWorkerResponse).toList();
    }

    public Map<String, Object> getDashboard(String brokerId) {
        Broker broker = brokers.findByBrokerId(brokerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Broker not found"));
        List<OfflineWorker> brokerWorkers = workers.findByBrokerEntityId(broker.getId());
        long activeCount = brokerWorkers.stream().filter(w -> "ACTIVE".equalsIgnoreCase(w.getStatus())).count();
        double avgRating = brokerWorkers.stream().mapToDouble(w -> w.getRating() == null ? 0.0 : w.getRating()).average().orElse(0.0);
        return Map.of("workerCount", brokerWorkers.size(), "activeCount", activeCount, "commissionMTD", broker.getCommissionMTD(), "avgRating", avgRating);
    }

    private Broker findBroker(Long id) {
        return brokers.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Broker not found"));
    }

    private BrokerResponse toResponse(Broker b) {
        return new BrokerResponse(b.getId(), b.getBrokerId(), b.getName(), b.getDistrict(), b.getCity(), b.getStatus(), b.getPhone(), b.getTotalWorkers(), b.getCommissionMTD(), b.getSubmittedAt());
    }

    private OfflineWorkerResponse toWorkerResponse(OfflineWorker w) {
        return new OfflineWorkerResponse(w.getId(), w.getWorkerName(), w.getWorkerNic(), w.getDistrict(), w.getSkills(), w.getStatus(), w.getTotalJobs(), w.getRating(), w.getCommissionEarned());
    }
}
