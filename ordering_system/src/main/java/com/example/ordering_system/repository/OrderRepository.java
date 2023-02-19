package com.example.ordering_system.repository;

import com.example.ordering_system.model.Order;
import com.example.ordering_system.model.User;
import org.aspectj.weaver.ast.Or;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public Optional<Order> findByOrderNumber(String orderNUmber);


}
