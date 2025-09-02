package test.billingservice.config;

@Service
public class OrdersClient {
  private final WebClient client;
  public OrdersClient(WebClient ordersWebClient) { this.client = ordersWebClient; }

  public List<OrderDTO> fetchAllOrders() {
    return client.get()
        .uri("/api/internal/orders")
        .retrieve()
        .bodyToFlux(OrderDTO.class)
        .collectList()
        .block();
  }
}
