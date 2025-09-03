package test.billingservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import test.billingservice.config.OrdersClient;
import test.billingservice.dtos.OrderDTO;

import java.util.List;

@RestController
public class BillingController {

    private final OrdersClient ordersClient;

    public BillingController(OrdersClient ordersClient) {
        this.ordersClient = ordersClient;
    }

    @GetMapping("/billing/orders")
    public List<OrderDTO> getOrders() {
        return ordersClient.fetchAllOrders();
    }
}
