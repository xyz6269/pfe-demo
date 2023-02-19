package com.example.ordering_system.service;

import com.example.ordering_system.dto.OrderRequest;
import com.example.ordering_system.dto.OrderedBooksDto;
import com.example.ordering_system.dto.UserDto;
import com.example.ordering_system.model.Order;
import com.example.ordering_system.model.OrderedBooks;
import com.example.ordering_system.model.User;
import com.example.ordering_system.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderedBooksList(orderRequest.getOrderedBooksDtos()
                        .stream()
                        .map(this::maptoOrder)
                        .collect(Collectors.toList())
                )
                .TotalPrice(orderRequest.getOrderedBooksDtos()
                        .stream().mapToDouble(OrderedBooksDto::getPrice).sum()
                )
                .user(usermapping(orderRequest.getUserDto()))
                .build();

        orderRepository.save(order);
    }


    public void deleteOrder(Order order) {
        orderRepository.delete(orderRepository.findById(order.getId()).orElseThrow(()-> new RuntimeException("NO order with this id: "+order.getId())));
    }

    public  Order getOrderbyId(long id) {
        return orderRepository.findById(id).orElseThrow(()-> new RuntimeException("NO order with this id: "+id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public String ValditeOrder(long id) {
        orderRepository.findById(id);

        return "this order has been validated by the administration " +
                "please head to library on site to claim it";
    }

    public String RejectOrder(long id) {
        deleteOrder(orderRepository.findById(id).orElseThrow(()-> new RuntimeException()));

        return "this order has been unfortunately rejected by the system";
    }

    public List<OrderedBooks> getOrder(String orderNumber) {
       return orderRepository
               .findByOrderNumber(orderNumber)
               .orElseThrow(RuntimeException::new)
               .getOrderedBooksList();
    }

    public OrderedBooks maptoOrder(OrderedBooksDto orderedItemsDto) {
        OrderedBooks orderedBooks = OrderedBooks.builder()
                .id(orderedItemsDto.getId())
                .isbn(orderedItemsDto.getIsbn())
                .price(orderedItemsDto.getPrice())
                .quantity(orderedItemsDto.getQuantity())
        .build();
        return orderedBooks;
    }

    public User usermapping(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .build();

        return user;
    }

}
