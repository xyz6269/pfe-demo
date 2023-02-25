package com.example.library_management.controller.orders_controller;


import com.example.library_management.DTO.order_DTO.OrderRequest;
import com.example.library_management.entity.order_entities.Order;
import com.example.library_management.entity.order_entities.OrderedBooks;
import com.example.library_management.service.order_service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth/order")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest request){
        orderService.placeOrder(request);
        return "Order placed successfully";
    }

    @GetMapping
    public List<Order> getALlorders(){
        return orderService.getAllOrders();
    }

    @GetMapping("{num}")
    public ResponseEntity<List<OrderedBooks>> GetEOrderbyNum(@PathVariable("num") String ordernum) {
        return new ResponseEntity<>(orderService.getOrder(ordernum),HttpStatus.OK);
    }

    @DeleteMapping("/reject/{id}")
    public ResponseEntity<Order> rejectOrder(@PathVariable("id") long id) {
        orderService.RejectOrder(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable("id") long id) {
       orderService.deleteOrder(orderService.getOrderbyId(id));

       return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/validate/{id}")
    public ResponseEntity<String> validation(@PathVariable("id") long id) {
        orderService.ValditeOrder(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
