package com.example.ordering_system;

import com.example.ordering_system.model.Order;
import com.example.ordering_system.model.OrderedBooks;
import com.example.ordering_system.model.User;
import com.example.ordering_system.repository.OrderRepository;
import com.example.ordering_system.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OrderingSystemApplicationTests {

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:11")
			.withDatabaseName("lib_orders")
			.withUsername("postgres")
			.withPassword("lolmao")
			.withExposedPorts(5432);

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderService orderService ;

	@DynamicPropertySource
	static void registerPgProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", () -> String.format("jdbc:postgresql://localhost:%d/lib_orders", postgres.getFirstMappedPort()));
		registry.add("spring.datasource.username", () -> "postgres");
		registry.add("spring.datasource.password", () -> "lolmao");
	}

	@Test
	void shouldCreateOrder() throws Exception {
		String orderRequestString = objectMapper.writeValueAsString(
				Order.builder()
						.orderNumber(UUID.randomUUID().toString())
						.orderedBooksList(getBooksList())
						.user(User.builder()
								.email("test@email.com")
								.build())
						.build()
		);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order/order")
						.contentType(MediaType.APPLICATION_JSON)
						.content(orderRequestString))
				        .andExpect(status().isCreated());

		Assertions.assertThat(orderRepository.findAll().size() == 1);
	}

	private List<OrderedBooks> getBooksList() {
		List<OrderedBooks> booksList = new ArrayList<>();
		booksList.add(OrderedBooks.builder()
				.quantity(555)
				.isbn("testbook1")
				.price(5)
				.build());
		booksList.add(OrderedBooks.builder()
				.quantity(55)
				.isbn("testbook2")
				.price(5)
				.build());
		booksList.add(OrderedBooks.builder()
				.quantity(5)
				.isbn("testbook3")
				.price(5)
				.build());

		return booksList;
	}

}
