package test.orderservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.orderservice.dtos.OrderDTO;

import java.util.List;

@RestController
@RequestMapping("/api/internal")
public class OrderController {
    @GetMapping("/orders")
    @PreAuthorize("hasRole('ORDER_READ')")
    public List<OrderDTO> listAll(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        System.out.println(jwt.getClaimAsString("preferred_username"));
        return List.of(
                new OrderDTO("1", "desc"),
                new OrderDTO("2", "desc2")
        );
    }
}
