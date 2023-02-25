package com.example.library_management.service.order_service;

import com.example.library_management.DTO.order_DTO.OrderRequest;
import com.example.library_management.DTO.order_DTO.OrderedBooksDto;
import com.example.library_management.entity.order_entities.Order;
import com.example.library_management.entity.order_entities.OrderedBooks;
import com.example.library_management.repository.order_repositories.OrderRepository;
import com.example.library_management.service.user_service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AuthenticationService authenticationService;

    public void placeOrder(OrderRequest orderRequest) {
        log.info(orderRequest.toString());
        log.info(String.valueOf(authenticationService.getCurrentUser()));
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
                .user(authenticationService.getCurrentUser())
                .build();
        log.info(order.toString());
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
        .build();
        return orderedBooks;
    }



}
