package com.example.library_management.repository.order_repositories;


import com.example.library_management.entity.order_entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public Optional<Order> findByOrderNumber(String orderNUmber);

}
