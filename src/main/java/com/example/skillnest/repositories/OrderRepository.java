package com.example.skillnest.repositories;

import com.example.skillnest.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findByUserIdAndStatus(UUID userId, String status);
}
