package test.billingservice.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import test.billingservice.dtos.OrderDTO;

import java.util.List;

@Service
public class OrdersClient {

  private final WebClient client;

  public OrdersClient(WebClient ordersWebClient) {
    this.client = ordersWebClient;
  }

  public List<OrderDTO> fetchAllOrders() {
    return client.get()
            .uri("/api/internal/orders")
            .retrieve()
            .bodyToFlux(OrderDTO.class)
            .collectList()
            .block(); // blocking is OK for MVC app
  }
}
