package test.billingservice.controller;

import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.billingservice.dtos.OrderDTO;

import java.util.List;

@RestController
@RequestMapping("/api/internal")
public class OrderController {
    @GetMapping("/orders")
    @PreAuthorize("hasRole('ORDER_READ')") 
    public List<OrderDTO> listAll() {
        OrderDTO orderDTO = new OrderDTO("1", "desc");
        OrderDTO orderDTO2 = new OrderDTO("2", "desc2");
        return List.of(orderDTO, orderDTO2);
    }
}
