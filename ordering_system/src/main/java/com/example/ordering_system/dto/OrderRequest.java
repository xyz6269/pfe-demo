package com.example.ordering_system.dto;

import com.example.ordering_system.model.OrderedBooks;
import com.example.ordering_system.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private List<OrderedBooksDto> orderedBooksDtos;
    private UserDto userDto;
}
