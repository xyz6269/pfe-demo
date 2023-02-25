package com.example.library_management.entity.order_entities;

import com.example.library_management.entity.user_entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders")
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String orderNumber;
    @Column(nullable = false)
    private double TotalPrice;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderedBooks> orderedBooksList;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

}
