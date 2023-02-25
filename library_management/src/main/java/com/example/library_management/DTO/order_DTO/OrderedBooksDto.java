package com.example.library_management.DTO.order_DTO;

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

}
