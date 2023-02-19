package com.example.ordering_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedBooksDto {
    private long id;
    private double price;
    private String isbn;
    private Integer quantity;

}
